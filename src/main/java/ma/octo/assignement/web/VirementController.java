package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VirementRepository;
import ma.octo.assignement.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/virements")
class VirementController {

    public static final int MONTANT_MAXIMAL = 10000;

    Logger LOGGER = LoggerFactory.getLogger(VirementController.class);

    @Autowired
    private CompteRepository compteRep;
    @Autowired
    private VirementRepository virementRep;
    @Autowired
    private AuditService monservice;
    @Autowired
    private UtilisateurRepository userRepo;

    @GetMapping("lister_virements")
    List<Virement> loadAll() {
        List<Virement> all = virementRep.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    @GetMapping("lister_comptes")
    List<Compte> loadAllCompte() {
        List<Compte> all = compteRep.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    @GetMapping("lister_utilisateurs")
    List<Utilisateur> loadAllUtilisateur() {
        List<Utilisateur> all = userRepo.findAll();

        if (CollectionUtils.isEmpty(all)) {
            return null;
        } else {
            return all;
        }
    }

    @PostMapping("/executerVirements")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody VirementDto virementDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        Compte fromAccount = compteRep.findByNrCompte(virementDto.getNrCompteEmetteur());
        Compte toAccount = compteRep.findByNrCompte(virementDto.getNrCompteBeneficiaire());

        //Check if all conditions are satisfied
        //Exception will be throws is case of Error
        validateOperation(fromAccount,toAccount,virementDto);

        //Update Accounts
        updateAccounts(fromAccount, toAccount, virementDto);
    
        //Create Virement Instance
        Virement virement = new Virement();
        virement.setDateExecution(virementDto.getDate());
        virement.setCompteBeneficiaire(toAccount);
        virement.setCompteEmetteur(fromAccount);
        virement.setMontantVirement(virementDto.getMontantVirement());

        virementRep.save(virement);

        //Audit the virement
        auditVirement(virementDto);

    }

    /**
     *
     * @param fromAccount
     * the account from where we get the amount
     * @param virementDto
     * the infos about amount
     * @param toAccount
     * the account were we put the amount
     * @throws CompteNonExistantException
     * @throws TransactionException
     * @throws SoldeDisponibleInsuffisantException
     */
    private void validateOperation(Compte fromAccount, Compte toAccount , VirementDto virementDto)
            throws CompteNonExistantException, TransactionException, SoldeDisponibleInsuffisantException {
        // the Two Accounts should be existed
        if (fromAccount == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (toAccount == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        //Check Montant Constraints
        if (virementDto.getMontantVirement().equals(null) || virementDto.getMontantVirement().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        }
        else
        if (virementDto.getMontantVirement().intValue() < 10) {
            System.out.println("Montant minimal de virement non atteint");
            throw new TransactionException("Montant minimal de virement non atteint");
        }
        else
        if (virementDto.getMontantVirement().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de virement dépassé");
            throw new TransactionException("Montant maximal de virement dépassé");
        }

        //Check the motif
        if (virementDto.getMotif().length() < 0) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }

        //First account should have in sold at least Motant
        if (fromAccount.getSolde().intValue() - virementDto.getMontantVirement().intValue() < 0) {
            LOGGER.error("Solde insuffisant pour l'utilisateur");
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");
        }
    }

    /**
     *
     * @param fromAccount
     * the account from where we get the amount
     * @param virementDto
     * the infos about amount
     * @param toAccount
     * the account were we put the amount
     */
    private void updateAccounts(Compte fromAccount, Compte toAccount, VirementDto virementDto){

        fromAccount.setSolde(fromAccount.getSolde().subtract(virementDto.getMontantVirement()));
        compteRep.save(fromAccount);

        toAccount.setSolde(new BigDecimal(toAccount.getSolde().intValue() + virementDto.getMontantVirement().intValue()));
        compteRep.save(toAccount);
    }

    private void auditVirement(VirementDto virementDto){
        monservice.auditVirement("Virement depuis " + virementDto.getNrCompteEmetteur() + " vers " + virementDto
                .getNrCompteBeneficiaire() + " d'un montant de " + virementDto.getMontantVirement()
                .toString());
    }

}

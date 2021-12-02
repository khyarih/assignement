package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MappingException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.MapStructMapper;
import ma.octo.assignement.repository.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VirementService{

    public static final int MONTANT_MAXIMAL = 10000;

    private final VirementRepository repository;
    private final MapStructMapper mapper;
    private final AuditService auditService;
    private final CompteService compteService;

    @Autowired
    public VirementService(VirementRepository repository, MapStructMapper mapper, AuditService auditService, CompteService compteService){
        this.repository = repository;
        this.mapper = mapper;
        this.auditService = auditService;
        this.compteService = compteService;
    }

    public List<Virement> loadAll(){
        List<Virement> virements =repository.findAll();
        return virements.isEmpty() ? null : virements;
    }

    public void save(Virement virement){
        repository.save(virement);
    }

    public VirementDto executerVirement(VirementDto virementDto) throws TransactionException, CompteNonExistantException, SoldeDisponibleInsuffisantException {

        try{
            Virement virement = mapper.mapVirementDtoToVirement(virementDto);

            //Check if all conditions are satisfied
            //Exception will be throws is case of Error
            validateOperation(virement);

            //Update Accounts
            updateAccounts(virement);

            //save Virement Instance
            repository.save(virement);

            //Audit the virement
            auditVirement(virementDto);

            return mapper.mapVirementToVirementDto(virement);

        } catch (MappingException e) {
            throw new TransactionException(e.getMessage());
        }

    }


    /**
     *
     * @param virement
     * @throws TransactionException
     * @throws SoldeDisponibleInsuffisantException
     */
    private void validateOperation(Virement virement)
            throws TransactionException, SoldeDisponibleInsuffisantException {

        //Check Montant Constraints
        if (virement.getMontantVirement().intValue() == 0)
            throw new TransactionException("Montant vide");

        if (virement.getMontantVirement().intValue() < 10)
            throw new TransactionException("Montant minimal de virement non atteint");

        if (virement.getMontantVirement().intValue() > MONTANT_MAXIMAL)
            throw new TransactionException("Montant maximal de virement dépassé");

        if (virement.getMotifVirement().length() == 0)
            throw new TransactionException("Motif vide");

        //First account should have in sold at least Motant
        if (virement.getCompteEmetteur().getSolde().intValue() - virement.getMontantVirement().intValue() < 0)
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");

    }

    @Transactional(rollbackOn = {Exception.class})
    void updateAccounts(Virement virement){
        virement.getCompteEmetteur().setSolde(virement.getCompteEmetteur().getSolde().subtract(virement.getMontantVirement()));
        virement.getCompteBeneficiaire().setSolde(virement.getCompteBeneficiaire().getSolde().add(virement.getMontantVirement()));

        compteService.save(virement.getCompteEmetteur());
        compteService.save(virement.getCompteBeneficiaire());
    }

    /**
     *
     * @param virementDto
     */
    private void auditVirement(VirementDto virementDto){
        auditService.auditVirement("Virement depuis " + virementDto.getNrCompteEmetteur() + " vers " + virementDto
                .getNrCompteBeneficiaire() + " d'un montant de " + virementDto.getMontantVirement()
                .toString());
    }

}

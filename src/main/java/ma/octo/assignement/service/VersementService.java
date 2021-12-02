package ma.octo.assignement.service;

import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MappingException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.MapStructMapper;
import ma.octo.assignement.repository.VersementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VersementService {

    public static final int MONTANT_MAXIMAL = 10000;
    public static final int MONTANT_MINIMAL = 10;

    private final VersementRepository repository;
    private final MapStructMapper mapper;
    private final CompteService compteService;
    private final AuditService auditService;

    @Autowired
    public VersementService(VersementRepository repository, MapStructMapper mapper, CompteService compteService, AuditService auditService){
        this.repository = repository;
        this.mapper = mapper;
        this.compteService = compteService;
        this.auditService = auditService;
    }

    public List<Versement> loadAll(){
        List<Versement> versements = repository.findAll();

        return versements.isEmpty() ? null : versements;
    }

    public VersementDto executerVersement(VersementDto versementDto)throws CompteNonExistantException, TransactionException {

        try{
            //mapping to Versement

            Versement versement = mapper.mapVersementDtoToVersement(versementDto);

            //Check if all conditions are satisfied
            //Exception will be throws is case of Error
            validateVersement(versement);

            //Update Accounts
            updateAccounts(versement);

            repository.save(versement);

            //Audit the versement
            auditVersement(versementDto);

            return mapper.mapVersementToVersementDto(versement);

        }catch(MappingException e){
            throw new TransactionException(e.getMessage());
        }

    }

    /**
     *
     * @param versement
     * @throws CompteNonExistantException
     * @throws TransactionException
     */
    private void validateVersement(Versement versement)
            throws CompteNonExistantException, TransactionException {

        if(versement.getMontantVirement().intValue() == 0)
            throw new TransactionException("Montant vide");

        if (versement.getMontantVirement().intValue() < MONTANT_MINIMAL)
            throw new TransactionException("Montant minimal de versement non atteint");

        if (versement.getMontantVirement().intValue() > MONTANT_MAXIMAL)
            throw new TransactionException("Montant maximal de versement dépassé");

        //Check the motif
        if (versement.getMotifVersement().length() == 0)
            throw new TransactionException("Motif vide");

    }

    /**
     *
     * @param versement
     */
    @Transactional(rollbackOn = {Exception.class})
    void updateAccounts(Versement versement){

        versement.getCompteBeneficiaire().setSolde(versement.getCompteBeneficiaire().getSolde().add(versement.getMontantVirement()));
        compteService.save(versement.getCompteBeneficiaire());

    }

    /**
     *
     * @param versementDto
     * a VersementDto object contains infos about the versement
     */
    private void auditVersement(VersementDto versementDto){
        auditService.auditVersement("Versement par " + versementDto.getNom_prenom_emetteur()
                + " vers " + versementDto.getNrCompteBeneficiaire()
                + " d'un montant de " + versementDto.getMontantVersement().toString()
        );
    }


}

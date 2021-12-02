package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MappingException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MapStructMapperImpl implements MapStructMapper{

    private final CompteRepository compteRepository;
    private final VirementRepository virementRepository;

    @Autowired
    public MapStructMapperImpl(CompteRepository compteRepository, VirementRepository virementRepository) {
        this.compteRepository = compteRepository;
        this.virementRepository = virementRepository;
    }

    /**
     *
     * @param virementDto
     * @return virement
     * @throws MappingException
     * @throws CompteNonExistantException
     */
    @Override
    public Virement mapVirementDtoToVirement(VirementDto virementDto) throws MappingException, CompteNonExistantException {
        if (virementDto == null)
            return null;

        if(virementDto.getNrCompteEmetteur() == null)
            throw new MappingException("numero de compte emetteur est vide");

        if(virementDto.getNrCompteBeneficiaire() == null)
            throw new MappingException("numero de compte Binificiare est vide");

        if(virementDto.getMontantVirement() == null )
            throw new MappingException("Montant est vide");

        Compte fromAccount = compteRepository.findByNrCompte(virementDto.getNrCompteEmetteur());
        Compte toAccount = compteRepository.findByNrCompte(virementDto.getNrCompteBeneficiaire());

        if(fromAccount == null)
            throw new CompteNonExistantException("fromAccount doesn't exist");

        if(toAccount == null)
            throw new CompteNonExistantException("toAccount doesn't exist");

        Virement virement = new Virement();

        virement.setCompteEmetteur(fromAccount);
        virement.setCompteBeneficiaire(toAccount);
        virement.setMontantVirement(virementDto.getMontantVirement());
        virement.setMotifVirement(virementDto.getMotif());
        virement.setDateExecution(virementDto.getDate());

        return virement;
    }

    /**
     *
     * @param virement
     * @return virementDto
     * @throws MappingException
     */
    @Override
    public VirementDto mapVirementToVirementDto(Virement virement) throws MappingException {
        if(virement == null)
            return null;

        if(virement.getCompteEmetteur() == null)
            throw new MappingException("Compte emetteur is empty");

        if(virement.getCompteBeneficiaire() == null)
            throw new MappingException("Compte beneficiaire is empty");

        if(virement.getMontantVirement() == null)
            throw new MappingException("Montant  is empty");

        VirementDto virementDto = new VirementDto();

        virementDto.setNrCompteEmetteur(virement.getCompteEmetteur().getNrCompte());
        virementDto.setNrCompteBeneficiaire(virement.getCompteBeneficiaire().getNrCompte());
        virementDto.setMontantVirement(virement.getMontantVirement());
        virementDto.setMotif(virement.getMotifVirement());
        virementDto.setDate(virement.getDateExecution());

        return virementDto;
    }

    /**
     *
     * @param versementDto
     * @return versement
     * @throws MappingException
     * @throws CompteNonExistantException
     */
    @Override
    public Versement mapVersementDtoToVersement(VersementDto versementDto) throws MappingException, CompteNonExistantException {
        if(versementDto == null)
            return null;

        if(versementDto.getNrCompteBeneficiaire() == null)
            throw new MappingException("numero de compte beneficiare est vide");

        if(versementDto.getMontantVersement() == null)
            throw new MappingException("Montant est vide");

        if(versementDto.getNom_prenom_emetteur() == null)
            throw new MappingException("Nom_prenom est vide");

        Compte toAccount = compteRepository.findByNrCompte(versementDto.getNrCompteBeneficiaire());

        if(toAccount == null)
            throw new CompteNonExistantException("toAccount doesn't exist");

        Versement versement = new Versement();

        versement.setCompteBeneficiaire(toAccount);
        versement.setNom_prenom_emetteur(versementDto.getNom_prenom_emetteur());
        versement.setDateExecution(versementDto.getDate());
        versement.setMontantVirement(versementDto.getMontantVersement());
        versement.setMotifVersement(versementDto.getMotif());

        return versement;
    }

    /**
     *
     * @param versement
     * @return versmentDto
     * @throws MappingException
     */
    @Override
    public VersementDto mapVersementToVersementDto(Versement versement) throws MappingException {
        if(versement == null)
            return null;

        if(versement.getCompteBeneficiaire() == null)
            throw new MappingException("Compte est vide");

        if(versement.getNom_prenom_emetteur() == null)
            throw new MappingException("Nom_prenom est vide");

        if(versement.getMontantVirement() == null)
            throw new MappingException("Montant est vide");

        VersementDto versementDto = new VersementDto();

        versementDto.setNrCompteBeneficiaire(versement.getCompteBeneficiaire().getNrCompte());
        versementDto.setNom_prenom_emetteur(versement.getNom_prenom_emetteur());
        versementDto.setMontantVersement(versement.getMontantVirement());
        versementDto.setMotif(versement.getMotifVersement());
        versementDto.setDate(versement.getDateExecution());

        return versementDto;
    }
}

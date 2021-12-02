package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MappingException;


public interface MapStructMapper {

    public Virement mapVirementDtoToVirement(VirementDto virementDto) throws MappingException, CompteNonExistantException;
    public VirementDto mapVirementToVirementDto(Virement virement) throws MappingException;

    public Versement mapVersementDtoToVersement(VersementDto versementDto) throws MappingException, CompteNonExistantException;
    public VersementDto mapVersementToVersementDto(Versement versement) throws MappingException;

}

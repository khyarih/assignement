package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.MappingException;
import ma.octo.assignement.repository.CompteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.when;


@SpringBootTest
public class MapStructMapperTest {

    @Mock
    private VirementDto virementDtoMock ;

    private MapStructMapper mapper;

//
//    private static Compte compteEmeteur;
//    private Compte compteBeneficiaire;
//
//    @Mock
//    private CompteRepository compteRepository;

    private static VirementDto virementDto;

    @Autowired
    public MapStructMapperTest(MapStructMapper mapper) {
        this.mapper = mapper;
    }

    @BeforeEach
    public void init() {
        virementDto = new VirementDto();

        final String nrCompteEmetteur = "compte_emeteur_nr";
        final String nrCompteBeneficiaire = "compte_beneficiaire_nr";
        final BigDecimal montantVirement = new BigDecimal(12.3);
        final String motifVirement = "virement_motif";
        final Date dateVirement = new Date();

        virementDto.setNrCompteEmetteur(nrCompteEmetteur);
        virementDto.setNrCompteBeneficiaire(nrCompteBeneficiaire);
        virementDto.setMontantVirement(montantVirement);
        virementDto.setMotif(motifVirement);
        virementDto.setDate(dateVirement);
    }

    @Test
    void mapVirementDtoToVirementTest_Should_Return_Null_If_VirementDto_Is_Null() {

        //should return null if [virementDto] is null
        Assertions.assertAll(() -> {

            VirementDto nullVirementDto = null;
            //should return null if [virementDto] is null
            Assertions.assertNull(mapper.mapVirementDtoToVirement(nullVirementDto));

        });
    }

    @Test
    public void mapVirementDtoToVirementTest_Should_Throws_Exception_If_Required_Field_Null() {
        
        //should throw [Exception] if one of [virementDto] fields are null
        Assertions.assertAll(

                //Should throws [MappingException] if getNrCompteEmetteur return null
                () -> {
                    when(virementDtoMock.getNrCompteEmetteur()).thenReturn(null);
                    Assertions.assertThrows(MappingException.class, () -> {
                        mapper.mapVirementDtoToVirement(virementDto);
                    });
                },

                //Should throws [CompteNonExistException] if getNrCompteEmetteur return null
                () -> {
                    when(virementDto.getNrCompteEmetteur()).thenReturn(null);

                    Assertions.assertThrows(CompteNonExistantException.class, () -> {
                        mapper.mapVirementDtoToVirement(virementDto);
                    });
                },

                //Should throws [CompteNonExistException] if virementDto.getNrCompteBeneficiaire return null
                () -> {
                    when(virementDto.getNrCompteBeneficiaire()).thenReturn(null);

                    Assertions.assertNotNull(virementDto.getNrCompteEmetteur());
                    Assertions.assertThrows(CompteNonExistantException.class, () -> {
                        mapper.mapVirementDtoToVirement(virementDto);
                    });
                },

                //Should throws [MontantException] if virementDto.getMontantVirement return null
                () -> {
                    when(virementDto.getMontantVirement()).thenReturn(null);

                    Assertions.assertNotNull(virementDto.getNrCompteEmetteur());
                    Assertions.assertNotNull(virementDto.getNrCompteBeneficiaire());
                    Assertions.assertThrows(CompteNonExistantException.class, () -> {
                        mapper.mapVirementDtoToVirement(virementDto);
                    });
                }

        );
    }

    @Test
    public void mapVirementDtoToVirementTest_Should_Return_Vurement_With_The_Expected_Data() {

        //fields should be the same as [virementDto]
        Assertions.assertAll(() -> {

            Virement result = mapper.mapVirementDtoToVirement(virementDto);

            Assertions.assertEquals(result.getCompteEmetteur().getNrCompte(), virementDto.getNrCompteEmetteur());
            Assertions.assertEquals(result.getCompteBeneficiaire().getNrCompte(), virementDto.getNrCompteBeneficiaire());
            Assertions.assertEquals(result.getMontantVirement(), virementDto.getMontantVirement());
            Assertions.assertEquals(result.getMotifVirement(), virementDto.getMotif());
            Assertions.assertEquals(result.getDateExecution(), virementDto.getDate());
        });

    }

    @Test
    public void mapVirementToVirementDtoTest() {
        //TODO:
    }

}
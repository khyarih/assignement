package ma.octo.assignement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class VersementDto {

    private String nrCompteBeneficiaire;
    private String nom_prenom_emetteur;
    private BigDecimal montantVersement;
    private String motif;
    private Date date;

}

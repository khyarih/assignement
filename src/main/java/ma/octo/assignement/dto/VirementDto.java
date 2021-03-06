package ma.octo.assignement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class VirementDto {

  private String nrCompteEmetteur;
  private String nrCompteBeneficiaire;
  private String motif;
  private BigDecimal montantVirement;
  private Date date;

}

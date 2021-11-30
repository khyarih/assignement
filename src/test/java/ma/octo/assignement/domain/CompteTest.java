package ma.octo.assignement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CompteTest {

    @Test
    public void shouldGetCompteObjectProperties() throws Exception {

        Long id = 1L;
        BigDecimal solde = BigDecimal.valueOf(100);
        String nrCompte = "123456789";
        Utilisateur utilisateur = new Utilisateur();

        Compte compte = new Compte();
        compte.setId(id);
        compte.setSolde(solde);
        compte.setNrCompte(nrCompte);
        compte.setUtilisateur(utilisateur);

        Assertions.assertTrue(compte.getId() == id);
        Assertions.assertTrue(compte.getSolde() == solde);
        Assertions.assertTrue(compte.getNrCompte() == nrCompte);
        Assertions.assertTrue(compte.getUtilisateur() == utilisateur);
    }

}

package ma.octo.assignement;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.UtilisateurService;
import ma.octo.assignement.service.VirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class AssignementApplication implements CommandLineRunner {

	private final UtilisateurService userService;
	private final CompteService compteService;
	private final VirementService virementService;

	@Autowired
	public AssignementApplication(UtilisateurService userService, CompteService compteService, VirementService virementService) {
		this.userService = userService;
		this.compteService = compteService;
		this.virementService = virementService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AssignementApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Utilisateur utilisateur1 = new Utilisateur();
		utilisateur1.setUsername("user1");
		utilisateur1.setLastname("last1");
		utilisateur1.setFirstname("first1");
		utilisateur1.setGender("Male");

		userService.save(utilisateur1);


		Utilisateur utilisateur2 = new Utilisateur();
		utilisateur2.setUsername("user2");
		utilisateur2.setLastname("last2");
		utilisateur2.setFirstname("first2");
		utilisateur2.setGender("Female");

		userService.save(utilisateur2);

		Compte compte1 = new Compte();
		compte1.setNrCompte("010000A000001000");
		compte1.setRib("RIB1");
		compte1.setSolde(BigDecimal.valueOf(200000L));
		compte1.setUtilisateur(utilisateur1);

		compteService.save(compte1);

		Compte compte2 = new Compte();
		compte2.setNrCompte("010000B025001000");
		compte2.setRib("RIB2");
		compte2.setSolde(BigDecimal.valueOf(140000L));
		compte2.setUtilisateur(utilisateur2);

		compteService.save(compte2);

		Virement v = new Virement();
		v.setMontantVirement(BigDecimal.TEN);
		v.setCompteBeneficiaire(compte2);
		v.setCompteEmetteur(compte1);
		v.setDateExecution(new Date());
		v.setMotifVirement("Assignment 2021");

		virementService.save(v);
	}
}

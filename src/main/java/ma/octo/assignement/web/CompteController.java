package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {

    private final CompteService service;

    @Autowired
    public CompteController(CompteService service) {
        this.service = service;
    }

    @GetMapping("lister_comptes")
    List<Compte> loadAllCompte() {
       return service.loadAll();
    }

}

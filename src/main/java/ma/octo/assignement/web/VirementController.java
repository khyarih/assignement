package ma.octo.assignement.web;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VirementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.VirementRepository;
import ma.octo.assignement.service.AuditService;
import ma.octo.assignement.service.VirementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/virements")
class VirementController {

    private final VirementService service;

    @Autowired
    public VirementController(VirementService service) {
        this.service = service;
    }

    @GetMapping("/lister_virements")
    List<Virement> loadAll() {
        return service.loadAll();
    }



    @PostMapping("/executer_virement")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public VirementDto createTransaction(@ModelAttribute VirementDto virementDto){
        try{

            return service.executerVirement(virementDto);

        }catch(TransactionException e){


        }catch(CompteNonExistantException e){

            //TODO:

        }catch (Exception e){

            //TODO

        }
        return null;
    }

}

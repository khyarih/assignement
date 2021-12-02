package ma.octo.assignement.web;

import ma.octo.assignement.domain.Versement;
import ma.octo.assignement.domain.Virement;
import ma.octo.assignement.dto.VersementDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.VersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/versements")
public class VersementController {

    private final VersementService service;

    @Autowired
    public VersementController(VersementService service){

        this.service = service;
    }

    @GetMapping("/lister_versements")
    List<Versement> loadAll() {
        return service.loadAll();
    }

    @PostMapping(value="/executer_versement")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public VersementDto createTransaction(@ModelAttribute VersementDto versementDto){

        try{

            return service.executerVersement(versementDto);

        }catch(TransactionException e){

            //TODO:

        }catch(CompteNonExistantException e){

            //TODO:

        }catch (Exception e){

            //TODO

        }
        return null;

    }

}

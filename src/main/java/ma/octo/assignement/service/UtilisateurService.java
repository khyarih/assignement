package ma.octo.assignement.service;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repository;

    @Autowired
    public UtilisateurService(UtilisateurRepository repository){
        this.repository = repository;
    }

    public List<Utilisateur> loadAll(){
        List<Utilisateur> users = repository.findAll();
        return users.isEmpty() ? null : users;
    }
}

package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteService {

    private final CompteRepository repository;

    @Autowired
    public CompteService(CompteRepository repository){
        this.repository = repository;
    }

    public List<Compte> loadAll(){
        List<Compte> comptes = repository.findAll();
        return comptes.isEmpty() ? null : comptes;
    }

    public Compte getByNrCompte(String nrCompte){
        return repository.findByNrCompte(nrCompte);
    }

    public void save(Compte compte) {
        repository.save(compte);
    }
}

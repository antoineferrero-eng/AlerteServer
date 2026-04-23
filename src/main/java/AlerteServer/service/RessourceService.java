package AlerteServer.service;

import AlerteServer.entity.Ressource;
import AlerteServer.entity.Site;
import AlerteServer.exeption.IdNotFoundException;
import AlerteServer.repository.RessourceRepository;
import AlerteServer.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RessourceService {

    @Autowired
    private RessourceRepository ressourceRepository;

    public List<Ressource> getAll() {
        return ressourceRepository.findAll();
    }

    public Ressource getById(String id) {
        return ressourceRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Ressource not found: " + id));
    }
}

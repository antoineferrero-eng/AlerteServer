package AlerteServer.service;

import AlerteServer.dto.SiteDTO;
import AlerteServer.entity.Site;
import AlerteServer.exception.IdNotFoundException;
import AlerteServer.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public List<SiteDTO> getAll() {
        return siteRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public SiteDTO getById(String id) {
        return siteRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new IdNotFoundException("Site not found: " + id));
    }

    private SiteDTO mapToDTO(Site site) {
        String parentId = (site.getParent() != null) ? site.getParent().getDkCode() : null;
        String deptNum = (site.getDepartement() != null) ? site.getDepartement().getNum() : null;
        return new SiteDTO(site.getDkCode(), deptNum, parentId);
    }
}
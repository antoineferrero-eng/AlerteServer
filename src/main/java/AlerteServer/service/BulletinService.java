package AlerteServer.service;

import AlerteServer.repository.BulletinRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BulletinService {

    @Autowired
    private BulletinRepository bulletinRepository;

    private final ObjectMapper objectMapper;

    public BulletinService(BulletinRepository repository, ObjectMapper objectMapper) {
        this.bulletinRepository = repository;
        this.objectMapper = objectMapper;
    }

    public JsonNode getAll() throws Exception {
        String jsonRaw = bulletinRepository.findAllBulletinsJson();
        return (jsonRaw != null) ? objectMapper.readTree(jsonRaw) : objectMapper.createArrayNode();
    }

    public JsonNode getById(Long id) throws Exception {
        String jsonRaw = bulletinRepository.findBulletinByIdJson(id);
        return (jsonRaw != null) ? objectMapper.readTree(jsonRaw) : objectMapper.createObjectNode();
    }

    public JsonNode getByDep(String dep) throws Exception {
        String jsonRaw = bulletinRepository.findBulletinByDepJson(dep);
        return (jsonRaw != null) ? objectMapper.readTree(jsonRaw) : objectMapper.createArrayNode();
    }
}
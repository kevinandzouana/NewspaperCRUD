package newspapercrud.domain.service;


import newspapercrud.dao.NewspaperRepository;
import newspapercrud.dao.model.NewspaperEntity;
import newspapercrud.domain.model.NewspaperDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewspaperService {

      private final NewspaperRepository newspaperRepository;

    public NewspaperService(NewspaperRepository newspaperRepository) {
        this.newspaperRepository = newspaperRepository;
    }

    public List<NewspaperDTO> getAll() {
        List<NewspaperEntity> newspapers = newspaperRepository.getAll();
        List<NewspaperDTO> newspapersUI = new ArrayList<>();
        // Get type name and newspaper name from the database
        newspapers.forEach(newspaper -> {
            NewspaperDTO newspaperUI = new NewspaperDTO();
            newspaperUI.setId(newspaper.getId());
            newspaperUI.setName(newspaper.getName());
            newspapersUI.add(newspaperUI);
        });
        return newspapersUI;
    }


}

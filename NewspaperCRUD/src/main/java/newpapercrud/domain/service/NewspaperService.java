package newspapercrud.domain.service;


import jakarta.inject.Inject;
import newspapercrud.dao.NewspaperRepository;
import newspapercrud.dao.model.NewspaperEntity;
import newspapercrud.domain.model.NewspaperDTO;

import java.util.ArrayList;
import java.util.List;

public class NewspaperService {

      private final NewspaperRepository newspaperRepository;

    @Inject
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

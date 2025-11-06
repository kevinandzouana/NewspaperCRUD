package newspapercrud.domain.service;

import newspapercrud.dao.TypeRepository;
import newspapercrud.dao.model.TypeEntity;
import newspapercrud.domain.model.TypeDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

      private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<TypeDTO> getAll() {
        List<TypeEntity> types = typeRepository.getAll();
        List<TypeDTO> typesUI = new ArrayList<>();
        // Get type name and newspaper name from the database
        types.forEach(type -> {
            TypeDTO typeUI = new TypeDTO();
            typeUI.setId(type.getId());
            typeUI.setName(type.getDescription());
            typesUI.add(typeUI);
        });
        return typesUI;
    }


}

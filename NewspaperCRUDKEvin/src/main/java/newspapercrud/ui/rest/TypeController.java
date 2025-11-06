package newspapercrud.ui.rest;

import newspapercrud.domain.model.TypeDTO;
import newspapercrud.domain.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
@CrossOrigin(origins = "*")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        return ResponseEntity.ok(typeService.getAll());
    }
}


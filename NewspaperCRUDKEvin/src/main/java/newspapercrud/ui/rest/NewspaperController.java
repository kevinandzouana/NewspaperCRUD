package newspapercrud.ui.rest;

import newspapercrud.domain.model.NewspaperDTO;
import newspapercrud.domain.service.NewspaperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newspapers")
@CrossOrigin(origins = "*")
public class NewspaperController {

    private final NewspaperService newspaperService;

    public NewspaperController(NewspaperService newspaperService) {
        this.newspaperService = newspaperService;
    }

    @GetMapping
    public ResponseEntity<List<NewspaperDTO>> getAllNewspapers() {
        return ResponseEntity.ok(newspaperService.getAll());
    }
}


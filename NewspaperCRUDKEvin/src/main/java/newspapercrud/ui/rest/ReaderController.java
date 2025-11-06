package newspapercrud.ui.rest;

import newspapercrud.domain.model.ReaderArticleDTO;
import newspapercrud.domain.model.ReaderDTO;
import newspapercrud.domain.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readers")
@CrossOrigin(origins = "*")
public class ReaderController {

    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public ResponseEntity<List<ReaderDTO>> getAllReaders() {
        return ResponseEntity.ok(readerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReaderArticleDTO> getReader(@PathVariable int id) {
        return ResponseEntity.ok(readerService.get(id));
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<ReaderArticleDTO>> getReadersByArticle(@PathVariable int articleId) {
        return ResponseEntity.ok(readerService.getAllReadersByArticle(articleId));
    }

    @PostMapping
    public ResponseEntity<Integer> addReader(@RequestBody ReaderDTO reader) {
        int id = readerService.addReader(reader);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable int id) {
        readerService.deleteReader(id);
        return ResponseEntity.noContent().build();
    }
}


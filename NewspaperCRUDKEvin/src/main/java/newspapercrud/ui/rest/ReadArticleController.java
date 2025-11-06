package newspapercrud.ui.rest;

import newspapercrud.domain.model.ReaderArticleDTO;
import newspapercrud.domain.service.ReadArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/readarticles")
@CrossOrigin(origins = "*")
public class ReadArticleController {

    private final ReadArticleService readArticleService;

    public ReadArticleController(ReadArticleService readArticleService) {
        this.readArticleService = readArticleService;
    }

    @PostMapping
    public ResponseEntity<Integer> addReadArticle(@RequestBody ReaderArticleDTO readerArticle) {
        int result = readArticleService.addReadArticle(readerArticle);
        if (result == -2) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping
    public ResponseEntity<Void> updateReadArticle(@RequestBody ReaderArticleDTO readerArticle) {
        readArticleService.updateReadArticle(readerArticle);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReadArticle(@RequestBody ReaderArticleDTO readerArticle) {
        readArticleService.delete(readerArticle);
        return ResponseEntity.noContent().build();
    }
}


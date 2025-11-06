package newspapercrud.ui.rest;

import newspapercrud.domain.model.ArticleDTO;
import newspapercrud.domain.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return ResponseEntity.ok(articleService.getArticles());
    }

    @PostMapping
    public ResponseEntity<Integer> addArticle(@RequestBody ArticleDTO article) {
        int id = articleService.addArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable int id, @RequestBody ArticleDTO article) {
        article.setId(id);
        articleService.updateArticle(article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id, @RequestParam(defaultValue = "false") boolean confirmation) {
        articleService.deleteArticle(id, confirmation);
        return ResponseEntity.noContent().build();
    }
}


package newspapercrud.domain.service;

import newspapercrud.dao.ArticleRepository;
import newspapercrud.dao.ReadArticleRepository;
import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.ReadArticleEntity;
import newspapercrud.dao.model.TypeEntity;
import newspapercrud.domain.mappers.ArticleMapper;
import newspapercrud.domain.model.ArticleDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ReadArticleRepository readArticleRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, ReadArticleRepository readArticleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.readArticleRepository = readArticleRepository;
        this.articleMapper = articleMapper;
    }

    public List<ArticleDTO> getArticles() {
        List<ArticleEntity> articles = articleRepository.getAll();

        Map<Integer, Double> avgByArticleId = readArticleRepository.getAllByArticleId().stream()
                .collect(Collectors.toMap(ReadArticleEntity::getArticleId,
                        r -> (double) r.getRanking()));

        return articles.stream()
                .map(entity -> {
                    ArticleDTO dto = articleMapper.entityToDto(entity);
                    dto.setAvgRating(avgByArticleId.getOrDefault(entity.getId(), 0d));
                    return dto;
                })
                .collect(Collectors.toList());
    }



    public int addArticle(ArticleDTO articleUI) {
        ArticleEntity article = new ArticleEntity(articleUI.getId(), articleUI.getName(), new TypeEntity(articleUI.getTypeDTO().getId(),""), articleUI.getNpaperId());
        return articleRepository.save(article);
    }

    public void updateArticle(ArticleDTO articleUI) {
        ArticleEntity article = new ArticleEntity(articleUI.getId(), articleUI.getName(), new TypeEntity(articleUI.getTypeDTO().getId(), articleUI.getTypeDTO().getName()), articleUI.getNpaperId());
        articleRepository.update(article);
    }

    public void deleteArticle(int articleId, boolean confirmation) {
        articleRepository.delete(articleId,confirmation);
    }
}

package newspapercrud.dao.springJDBC;

import newspapercrud.common.Constantes;
import newspapercrud.dao.ArticleRepository;
import newspapercrud.dao.mappers.springmappers.ArticleRowMapper;
import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.error.DataBaseError;
import newspapercrud.domain.error.ForeignKeyError;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("notInUse")
public class SpringArticleRepository implements ArticleRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final JdbcClient jdbcClient;
    private final ArticleRowMapper articleRowMapper;

    public SpringArticleRepository(JdbcClient jdbcClient, ArticleRowMapper articleRowMapper) {
        this.jdbcClient = jdbcClient;
        this.articleRowMapper = articleRowMapper;
    }

    @Override
    public List<ArticleEntity> getAll() {
        try {
            return jdbcClient.sql(SQLQueries.GET_ALL_ARTICLES)
                    .query(articleRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public int save(ArticleEntity article) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcClient.sql(SQLQueries.INSERT_ARTICLE)
                    .param(article.getName())
                    .param(article.getType().getId())
                    .param(article.getNPaperID())
                    .update(keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                article.setId(key.intValue());
                return article.getId();
            }
            throw new DataBaseError(Constantes.ERROR_GENERATING_KEY);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public void update(ArticleEntity article) {
        try {
            jdbcClient.sql(SQLQueries.UPDATE_ARTICLE)
                    .param(article.getName())
                    .param(article.getType().getId())
                    .param(article.getNPaperID())
                    .param(article.getId())
                    .update();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public boolean delete(int articleId, boolean confirmation) {
        try {
            if (confirmation) {
                jdbcClient.sql(SQLQueries.DELETE_ARTICLE_READARTICLES)
                        .param(articleId)
                        .update();
            }

            int result = jdbcClient.sql(SQLQueries.DELETE_ARTICLE)
                    .param(articleId)
                    .update();

            return result == 1;
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new ForeignKeyError();
            }
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }
}

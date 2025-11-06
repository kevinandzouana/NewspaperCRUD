package newspapercrud.dao.springJDBC;

import newspapercrud.common.Constantes;
import newspapercrud.dao.ReadArticleRepository;
import newspapercrud.dao.mappers.springmappers.ReadArticleRowMapper;
import newspapercrud.dao.model.ReadArticleEntity;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.error.DataBaseError;
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
public class SpringReadArticleRepository implements ReadArticleRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final JdbcClient jdbcClient;
    private final ReadArticleRowMapper readArticleRowMapper;

    public SpringReadArticleRepository(JdbcClient jdbcClient, ReadArticleRowMapper readArticleRowMapper) {
        this.jdbcClient = jdbcClient;
        this.readArticleRowMapper = readArticleRowMapper;
    }

    @Override
    public List<ReadArticleEntity> getAllByArticleId() {
        try {
            return jdbcClient.sql(SQLQueries.GET_READARTICLES_BY_ARTICLEID)
                    .query(readArticleRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public ReadArticleEntity get(int articleID, int readerID) {
        try {
            return jdbcClient.sql(SQLQueries.GET_READARTICLE)
                    .param(articleID)
                    .param(readerID)
                    .query(readArticleRowMapper)
                    .optional()
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public ReadArticleEntity getByIdArticle(int articleID) {
        try {
            return jdbcClient.sql(SQLQueries.GET_READARTICLES_BY_ARTICLEID)
                    .param(articleID)
                    .query(readArticleRowMapper)
                    .optional()
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public int save(ReadArticleEntity readArticle) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcClient.sql(SQLQueries.INSERT_READARTICLE)
                    .param(readArticle.getArticleId())
                    .param(readArticle.getReaderId())
                    .param(readArticle.getRanking())
                    .update(keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                readArticle.setId(key.intValue());
                return readArticle.getId();
            }
            throw new DataBaseError(Constantes.ERROR_GENERATING_KEY);
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                logger.log(Level.INFO, "Read article already marked...");
                return -2;
            }
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public void update(ReadArticleEntity readArticle) {
        try {
            jdbcClient.sql(SQLQueries.UPDATE_READARTICLE)
                    .param(readArticle.getRanking())
                    .param(readArticle.getArticleId())
                    .param(readArticle.getReaderId())
                    .update();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public boolean delete(ReadArticleEntity readArticle) {
        try {
            int result = jdbcClient.sql(SQLQueries.DELETE_READARTICLE)
                    .param(readArticle.getReaderId())
                    .param(readArticle.getArticleId())
                    .update();

            return result == 1;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }
}


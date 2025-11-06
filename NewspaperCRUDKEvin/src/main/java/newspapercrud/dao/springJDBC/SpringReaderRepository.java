package newspapercrud.dao.springJDBC;

import newspapercrud.common.Constantes;
import newspapercrud.dao.ReaderRepository;
import newspapercrud.dao.mappers.springmappers.ReaderRowMapper;
import newspapercrud.dao.model.ReaderEntity;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.error.DataBaseError;
import newspapercrud.domain.error.DuplicatedUsernameError;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("notInUse")
public class SpringReaderRepository implements ReaderRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final JdbcClient jdbcClient;
    private final ReaderRowMapper readerRowMapper;

    public SpringReaderRepository(JdbcClient jdbcClient, ReaderRowMapper readerRowMapper) {
        this.jdbcClient = jdbcClient;
        this.readerRowMapper = readerRowMapper;
    }

    @Override
    public List<ReaderEntity> getAll() {
        try {
            return jdbcClient.sql(SQLQueries.GET_ALL_READERS)
                    .query(readerRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public List<ReaderEntity> getAllByArticle(int articleId) {
        try {
            return jdbcClient.sql(SQLQueries.GET_ALL_READERS_BY_ARTICLEID)
                    .param(articleId)
                    .query(readerRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public ReaderEntity get(int readerID) {
        try {
            return jdbcClient.sql(SQLQueries.GET_READER_BY_ID)
                    .param(readerID)
                    .query(readerRowMapper)
                    .optional()
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public int save(ReaderEntity reader) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            // Insert reader
            jdbcClient.sql(SQLQueries.INSERT_READER)
                    .param(reader.getName())
                    .param(Date.valueOf(reader.getDob()))
                    .update(keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                reader.setId(key.intValue());
            } else {
                throw new DataBaseError(Constantes.ERROR_GENERATING_KEY);
            }

            // Insert credentials
            jdbcClient.sql(SQLQueries.INSERT_CREDENTIAL)
                    .param(reader.getCredential().getUserName())
                    .param(reader.getCredential().getPassword())
                    .param(reader.getId())
                    .update();

            return reader.getId();
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                throw new DuplicatedUsernameError();
            }
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public void update(ReaderEntity reader) {
        // Not implemented in original JDBC version
    }

    @Override
    public boolean delete(int readerId) {
        try {
            // Delete in order: readarticle, subscribe, login, reader
            jdbcClient.sql(SQLQueries.DELETE_READER_READARTICLES)
                    .param(readerId)
                    .update();

            jdbcClient.sql(SQLQueries.DELETE_READER_SUBSCRIPTIONS)
                    .param(readerId)
                    .update();

            jdbcClient.sql(SQLQueries.DELETE_READER_LOGIN)
                    .param(readerId)
                    .update();

            int result = jdbcClient.sql(SQLQueries.DELETE_READER)
                    .param(readerId)
                    .update();

            return result == 1;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }
}


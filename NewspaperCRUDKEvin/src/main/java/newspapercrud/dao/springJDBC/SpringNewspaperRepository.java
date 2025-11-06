package newspapercrud.dao.springJDBC;

import newspapercrud.common.Constantes;
import newspapercrud.dao.NewspaperRepository;
import newspapercrud.dao.mappers.springmappers.NewspaperRowMapper;
import newspapercrud.dao.model.NewspaperEntity;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.error.DataBaseError;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("notInUse")
public class SpringNewspaperRepository implements NewspaperRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final JdbcClient jdbcClient;
    private final NewspaperRowMapper newspaperRowMapper;

    public SpringNewspaperRepository(JdbcClient jdbcClient, NewspaperRowMapper newspaperRowMapper) {
        this.jdbcClient = jdbcClient;
        this.newspaperRowMapper = newspaperRowMapper;
    }

    @Override
    public List<NewspaperEntity> getAll() {
        try {
            return jdbcClient.sql(SQLQueries.GET_ALL_NEWSPAPERS)
                    .query(newspaperRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public List<NewspaperEntity> getAllByReader(int readerID) {
        try {
            return jdbcClient.sql(SQLQueries.GET_ALL_NEWSPAPERS_BY_READERID)
                    .param(readerID)
                    .query(newspaperRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public NewspaperEntity get(int newspaperID) {
        try {
            return jdbcClient.sql(SQLQueries.GET_NEWSPAPER_BY_ID)
                    .param(newspaperID)
                    .query(newspaperRowMapper)
                    .optional()
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public int save(NewspaperEntity newspaper) {
        // Not implemented in original JDBC version
        return 0;
    }

    @Override
    public void update(NewspaperEntity newspaper) {
        // Not implemented in original JDBC version
    }

    @Override
    public boolean delete(int newspaperId) {
        // Not implemented in original JDBC version
        return false;
    }
}


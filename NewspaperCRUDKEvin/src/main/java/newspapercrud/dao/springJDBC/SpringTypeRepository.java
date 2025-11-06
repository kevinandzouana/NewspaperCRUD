package newspapercrud.dao.springJDBC;

import newspapercrud.common.Constantes;
import newspapercrud.dao.TypeRepository;
import newspapercrud.dao.mappers.springmappers.TypeRowMapper;
import newspapercrud.dao.model.TypeEntity;
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
public class SpringTypeRepository implements TypeRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final JdbcClient jdbcClient;
    private final TypeRowMapper typeRowMapper;

    public SpringTypeRepository(JdbcClient jdbcClient, TypeRowMapper typeRowMapper) {
        this.jdbcClient = jdbcClient;
        this.typeRowMapper = typeRowMapper;
    }

    @Override
    public List<TypeEntity> getAll() {
        try {
            return jdbcClient.sql(SQLQueries.GET_ALL_TYPES)
                    .query(typeRowMapper)
                    .list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public TypeEntity get(int typeID) {
        try {
            return jdbcClient.sql(SQLQueries.GET_TYPE_BY_ID)
                    .param(typeID)
                    .query(typeRowMapper)
                    .optional()
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public int save(TypeEntity type) {
        // Not implemented in original JDBC version
        return 0;
    }

    @Override
    public void update(TypeEntity type) {
        // Not implemented in original JDBC version
    }

    @Override
    public boolean delete(int typeId, boolean confirmation) {
        // Not implemented in original JDBC version
        return false;
    }
}


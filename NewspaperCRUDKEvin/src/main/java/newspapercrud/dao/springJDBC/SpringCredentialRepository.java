package newspapercrud.dao.springJDBC;

import newspapercrud.common.Constantes;
import newspapercrud.dao.CredentialRepository;
import newspapercrud.dao.mappers.springmappers.CredentialRowMapper;
import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.CredentialEntity;
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
public class SpringCredentialRepository implements CredentialRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final JdbcClient jdbcClient;
    private final CredentialRowMapper credentialRowMapper;

    public SpringCredentialRepository(JdbcClient jdbcClient, CredentialRowMapper credentialRowMapper) {
        this.jdbcClient = jdbcClient;
        this.credentialRowMapper = credentialRowMapper;
    }

    @Override
    public List<CredentialEntity> getAll() {

        return List.of();
    }

    @Override
    public boolean delete(int reader_id) {

        return false;
    }

    @Override
    public void save(ArticleEntity patient) {

    }

    @Override
    public void update(CredentialEntity credential) {

    }

    @Override
    public CredentialEntity get(String username) {
        try {
            return jdbcClient.sql(SQLQueries.GET_CREDENTIAL)
                    .param(username)
                    .query(credentialRowMapper)
                    .optional()
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }
}


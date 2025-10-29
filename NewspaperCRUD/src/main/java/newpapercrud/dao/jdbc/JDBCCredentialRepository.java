package newspapercrud.dao.jdbc;

import jakarta.inject.Inject;
import newspapercrud.common.Constantes;
import newspapercrud.dao.CredentialRepository;
import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.CredentialEntity;
import newspapercrud.dao.utilities.DBConnectionPool;
import newspapercrud.dao.utilities.SQLQueries;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class JDBCCredentialRepository implements CredentialRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final DBConnectionPool dbConnection;

    @Inject
    public JDBCCredentialRepository(DBConnectionPool dbConnection) {
        this.dbConnection = dbConnection;
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
        CredentialEntity credential = null;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement getCredential = con.prepareStatement(SQLQueries.GET_CREDENTIAL)) {
            getCredential.setString(1, username);
            ResultSet rs = getCredential.executeQuery();
            if (rs.next()) {
                 credential = new CredentialEntity(
                         rs.getString("userlog"),
                         rs.getString("password"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return credential;
    }
}

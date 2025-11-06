package newspapercrud.dao.mappers.springmappers;

import newspapercrud.dao.model.CredentialEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CredentialRowMapper implements RowMapper<CredentialEntity> {

    @Override
    public CredentialEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CredentialEntity(
                rs.getString("userlog"),
                rs.getString("password")
        );
    }
}



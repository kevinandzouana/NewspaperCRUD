package newspapercrud.dao.mappers.springmappers;

import newspapercrud.dao.model.TypeEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TypeRowMapper implements RowMapper<TypeEntity> {

    @Override
    public TypeEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TypeEntity(
                rs.getInt("id"),
                rs.getString("description")
        );
    }
}


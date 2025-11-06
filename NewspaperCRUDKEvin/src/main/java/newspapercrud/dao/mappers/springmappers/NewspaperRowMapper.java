package newspapercrud.dao.mappers.springmappers;

import newspapercrud.dao.model.NewspaperEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NewspaperRowMapper implements RowMapper<NewspaperEntity> {

    @Override
    public NewspaperEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NewspaperEntity(
                rs.getInt("id"),
                rs.getString("name_newspaper"),
                rs.getDate("release_date").toLocalDate()
        );
    }
}


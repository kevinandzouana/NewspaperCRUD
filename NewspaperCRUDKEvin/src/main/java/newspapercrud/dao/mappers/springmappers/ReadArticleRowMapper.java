package newspapercrud.dao.mappers.springmappers;

import newspapercrud.dao.model.ReadArticleEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReadArticleRowMapper implements RowMapper<ReadArticleEntity> {

    @Override
    public ReadArticleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReadArticleEntity(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getInt(4)
        );
    }
}


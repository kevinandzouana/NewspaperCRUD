package newspapercrud.dao.mappers.springmappers;

import newspapercrud.dao.model.ReaderEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReaderRowMapper implements RowMapper<ReaderEntity> {

    @Override
    public ReaderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReaderEntity(
                rs.getInt("id"),
                rs.getString("name_reader"),
                rs.getDate("birth_reader").toLocalDate()
        );
    }
}
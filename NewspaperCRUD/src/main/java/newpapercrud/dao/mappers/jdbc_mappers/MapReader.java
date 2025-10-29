package newspapercrud.dao.mappers.jdbc_mappers;

import newspapercrud.dao.model.ReaderEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapReader {
    public List<ReaderEntity> mapReaders(ResultSet rs) {
        List<ReaderEntity> readers = new ArrayList<>();
        try {
            while (rs.next()) readers.add(new ReaderEntity(
                    rs.getInt("id"),
                    rs.getString("name_reader"),
                    rs.getDate("birth_reader").toLocalDate()));
            return readers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ReaderEntity mapSingleReader(ResultSet rs) throws SQLException {
        return new ReaderEntity(
                rs.getInt(1),
                rs.getString(2),
                rs.getDate(3).toLocalDate());
    }
}

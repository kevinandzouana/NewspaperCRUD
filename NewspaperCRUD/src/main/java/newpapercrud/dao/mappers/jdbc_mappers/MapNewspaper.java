package newspapercrud.dao.mappers.jdbc_mappers;

import newspapercrud.dao.model.NewspaperEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapNewspaper {
    public List<NewspaperEntity> mapNewspapers(ResultSet rs) {
        List<NewspaperEntity> newspapers = new ArrayList<>();
        try {
            while (rs.next()) newspapers.add(new NewspaperEntity(
                    rs.getInt("id"),
                    rs.getString("name_newspaper"),
                    rs.getDate("release_date").toLocalDate()));
            return newspapers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

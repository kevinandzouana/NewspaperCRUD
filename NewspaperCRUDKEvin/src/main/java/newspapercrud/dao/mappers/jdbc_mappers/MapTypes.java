package newspapercrud.dao.mappers.jdbc_mappers;

import newspapercrud.dao.model.TypeEntity;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class MapTypes {
    public List<TypeEntity> mapTypes(ResultSet rs) {
        List<TypeEntity> types = new ArrayList<>();
        try {
            while (rs.next()) types.add(new TypeEntity(
                    rs.getInt("id"),
                    rs.getString("description")));
            return types;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

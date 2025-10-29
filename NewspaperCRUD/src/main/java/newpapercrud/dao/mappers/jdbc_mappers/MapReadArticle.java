package newspapercrud.dao.mappers.jdbc_mappers;

import newspapercrud.dao.model.ReadArticleEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MapReadArticle {
    public List<ReadArticleEntity> mapReadArticles(ResultSet rs) {
        List<ReadArticleEntity> readArticles = new ArrayList<>();
        try {
            while (rs.next()) readArticles.add(new ReadArticleEntity(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getInt(4)));
            return readArticles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ReadArticleEntity mapSingleReadArticle(ResultSet rs) throws SQLException {
        return new ReadArticleEntity(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getInt(4)
        );
    }
}

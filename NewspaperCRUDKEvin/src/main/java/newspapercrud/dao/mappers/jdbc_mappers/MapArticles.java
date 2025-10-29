package newspapercrud.dao.mappers.jdbc_mappers;

import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.TypeEntity;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MapArticles {
    public List<ArticleEntity> mapRS(ResultSet articlesRS) {
        List<ArticleEntity> articles = new ArrayList<>();
        try {
            while (articlesRS.next()) {
                articles.add(new ArticleEntity(
                        articlesRS.getInt("id"),
                        articlesRS.getString("name_article"),
                        new TypeEntity(articlesRS.getInt("type_id"),articlesRS.getString("type_description")),
                        articlesRS.getInt("id_newspaper")));
            }
            return articles;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}

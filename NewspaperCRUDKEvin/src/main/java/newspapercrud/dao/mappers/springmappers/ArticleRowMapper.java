package newspapercrud.dao.mappers.springmappers;

import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.model.TypeEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleRowMapper implements RowMapper<ArticleEntity> {

    @Override
    public ArticleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ArticleEntity(
                rs.getInt("id"),
                rs.getString("name_article"),
                new TypeEntity(
                        rs.getInt("type_id"),
                        rs.getString("type_description")
                ),
                rs.getInt("id_newspaper")
        );
    }
}

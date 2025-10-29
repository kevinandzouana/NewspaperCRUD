package newspapercrud.dao.springJDBC;

import newspapercrud.dao.ArticleRepository;
import newspapercrud.dao.mappers.springmappers.ArticleRowMapper;
import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.mappers.ArticleMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
@Profile("inUse")
@Repository
public class SpringArticleRepository implements ArticleRepository {


    private JdbcClient jdbcClient;
    private final ArticleRowMapper articleRowMapper;

    public SpringArticleRepository(ArticleRowMapper articleRowMapper) {
        this.articleRowMapper = articleRowMapper;
    }

    @Override
    public List<ArticleEntity> getAll() {
        return jdbcClient.sql(SQLQueries.GET_ALL_ARTICLES)
                .query(articleRowMapper)
                .list();
    }

    @Override
    public int save(ArticleEntity article) {
        return 0;
    }

    @Override
    public void update(ArticleEntity article) {

    }

    @Override
    public boolean delete(int articleId, boolean confirmation) {
        return false;
    }
}

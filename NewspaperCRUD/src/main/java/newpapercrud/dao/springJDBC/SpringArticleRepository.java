package newspapercrud.dao.springJDBC;

import newspapercrud.dao.ArticleRepository;
import newspapercrud.dao.model.ArticleEntity;

import java.util.List;
@Profile("inUse")
public class SpringArticleRepository implements ArticleRepository {

    @Autowired
    private JdbcClient jdbcClient;



    @Override
    public List<ArticleEntity> getAll() {
        return List.of();
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

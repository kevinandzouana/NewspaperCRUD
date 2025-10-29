package newspapercrud.dao.jdbc;

import newspapercrud.common.Constantes;
import newspapercrud.dao.ArticleRepository;
import newspapercrud.dao.mappers.jdbc_mappers.MapArticles;
import newspapercrud.dao.model.ArticleEntity;
import newspapercrud.dao.utilities.DBConnectionPool;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.error.DataBaseError;
import newspapercrud.domain.error.ForeignKeyError;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCArticleRepository implements ArticleRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final MapArticles articlesMapper;
    private final DBConnectionPool pool;

    @Inject
    public JDBCArticleRepository(MapArticles articlesMapper, DBConnectionPool pool) {
        this.articlesMapper = articlesMapper;
        this.pool = pool;
    }


    @Override
    public List<ArticleEntity> getAll() {
        try (Connection con = pool.getConnection();
             Statement getArticles = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ) {
            ResultSet resultSet = getArticles.executeQuery(SQLQueries.GET_ALL_ARTICLES);
            return articlesMapper.mapRS(resultSet);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public int save(ArticleEntity article) {
        try (Connection con = pool.getConnection();
             PreparedStatement insertArticle = con.prepareStatement(SQLQueries.INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS)

        ) {
                insertArticle.setString(1, article.getName());
                insertArticle.setInt(2, article.getType().getId());
                insertArticle.setInt(3, article.getNPaperID());
                insertArticle.executeUpdate();
                ResultSet rs = insertArticle.getGeneratedKeys();
                rs.next();
                article.setId(rs.getInt(1));
                return article.getId();

        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }


    }

    @Override
    public void update(ArticleEntity article) {
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_ARTICLE)
        ) {
            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getType().getId());
            preparedStatement.setInt(3, article.getNPaperID());
            preparedStatement.setInt(4, article.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public boolean delete(int articleId, boolean confirmation) {
        int result = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement deleteArticle = con.prepareStatement(SQLQueries.DELETE_ARTICLE);
             PreparedStatement deleteReadArticle = con.prepareStatement(SQLQueries.DELETE_ARTICLE_READARTICLES)
        ) {
            try {
                con.setAutoCommit(false);
                if (confirmation) {
                    deleteReadArticle.setInt(1, articleId);
                    deleteReadArticle.executeUpdate();
               }
                deleteArticle.setInt(1, articleId);
                result = deleteArticle.executeUpdate();
                con.commit();
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                //logger.log(Level.SEVERE,e.getMessage(),e);
                throw new ForeignKeyError();
            } catch (SQLException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new DataBaseError(Constantes.DATA_BASE_ERROR);
            }
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
        return result == 1;
    }

}

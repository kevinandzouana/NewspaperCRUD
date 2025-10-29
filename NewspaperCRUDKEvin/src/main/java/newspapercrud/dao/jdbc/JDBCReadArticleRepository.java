

package newspapercrud.dao.jdbc;

import newspapercrud.common.Constantes;
import newspapercrud.dao.ReadArticleRepository;
import newspapercrud.dao.mappers.jdbc_mappers.MapReadArticle;
import newspapercrud.dao.model.ReadArticleEntity;
import newspapercrud.dao.utilities.DBConnectionPool;
import newspapercrud.dao.utilities.SQLQueries;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
@Profile("inUse")
public class JDBCReadArticleRepository implements ReadArticleRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final DBConnectionPool pool;
    private final MapReadArticle readArticleMapper;


    public JDBCReadArticleRepository(DBConnectionPool pool, MapReadArticle readArticleMapper) {
        this.pool = pool;
        this.readArticleMapper = readArticleMapper;
    }

    @Override
    public List<ReadArticleEntity> getAllByArticleId() {
        try (Connection con = pool.getConnection();
             Statement getPayments = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ) {
            ResultSet readArticleRS = getPayments.executeQuery(SQLQueries.GET_READARTICLES_BY_ARTICLEID);
            return readArticleMapper.mapReadArticles(readArticleRS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public ReadArticleEntity get(int articleID, int readerID) {
        ReadArticleEntity readArticle=null;
        try (Connection con = pool.getConnection();
             PreparedStatement getReadArticle = con.prepareStatement(SQLQueries.GET_READARTICLE)) {
            getReadArticle.setInt(1, articleID);
            getReadArticle.setInt(2, readerID);
            ResultSet rs=getReadArticle.executeQuery();
            readArticle=readArticleMapper.mapReadArticles(rs).stream().findFirst().orElse(null);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return readArticle;
    }


    @Override
    public ReadArticleEntity getByIdArticle(int articleID) {
        ReadArticleEntity readArticle=null;
        try (Connection con = pool.getConnection();
             PreparedStatement getReadArticle = con.prepareStatement(SQLQueries.GET_READARTICLES_BY_ARTICLEID)) {
            getReadArticle.setInt(1, articleID);
            ResultSet rs=getReadArticle.executeQuery();
            if(rs.next()) {
                readArticle = readArticleMapper.mapSingleReadArticle(rs);
            }
            //readArticle=readArticleMapper.mapReadArticles(rs).stream().findFirst().orElse(null);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return readArticle;
    }

    @Override
    public int save(ReadArticleEntity readArticle) {
        try (Connection con = pool.getConnection();
             PreparedStatement insertReadArticle = con.prepareStatement(SQLQueries.INSERT_READARTICLE, Statement.RETURN_GENERATED_KEYS)

        ) {
            insertReadArticle.setInt(1, readArticle.getArticleId());
            insertReadArticle.setInt(2, readArticle.getReaderId());
            insertReadArticle.setInt(3, readArticle.getRanking());
            insertReadArticle.executeUpdate();
            ResultSet rs = insertReadArticle.getGeneratedKeys();
            rs.next();
            readArticle.setId(rs.getInt(1));
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.log(Level.INFO, "Read article already marked...");
            return -2;

        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return readArticle.getId();

    }

    @Override
    public void update(ReadArticleEntity readArticle) {
        try (Connection con = pool.getConnection();
             PreparedStatement updateReadArticle = con.prepareStatement(SQLQueries.UPDATE_READARTICLE)
        ) {
            try {
                updateReadArticle.setInt(1, readArticle.getRanking());
                updateReadArticle.setInt(2, readArticle.getArticleId());
                updateReadArticle.setInt(3, readArticle.getReaderId());
                updateReadArticle.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
        }
    }

    @Override
    public boolean delete(ReadArticleEntity readArticle) {
        int result = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement deleteReadArticle = con.prepareStatement(SQLQueries.DELETE_READARTICLE)
        ) {
            try {
                deleteReadArticle.setInt(1, readArticle.getReaderId());
                deleteReadArticle.setInt(2, readArticle.getArticleId());
                result=deleteReadArticle.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
        }
        return result == 1;
    }

}




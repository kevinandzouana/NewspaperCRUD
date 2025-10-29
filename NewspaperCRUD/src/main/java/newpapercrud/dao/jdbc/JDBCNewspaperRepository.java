package newspapercrud.dao.jdbc;

import jakarta.inject.Inject;
import newspapercrud.common.Constantes;
import newspapercrud.dao.NewspaperRepository;
import newspapercrud.dao.mappers.jdbc_mappers.MapNewspaper;
import newspapercrud.dao.model.NewspaperEntity;
import newspapercrud.dao.utilities.DBConnectionPool;
import newspapercrud.dao.utilities.SQLQueries;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCNewspaperRepository implements NewspaperRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final DBConnectionPool pool;
    private final MapNewspaper newspaperMapper;

    @Inject
    public JDBCNewspaperRepository(DBConnectionPool pool, MapNewspaper newspaperMapper) {
        this.pool = pool;
        this.newspaperMapper = newspaperMapper;
    }

    @Override
    public List<NewspaperEntity> getAll() {
        try (Connection con = pool.getConnection();
             Statement getNewspapers = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ) {
            ResultSet newspaperRS = getNewspapers.executeQuery(SQLQueries.GET_ALL_NEWSPAPERS);
            return newspaperMapper.mapNewspapers(newspaperRS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public List<NewspaperEntity> getAllByReader(int readerID) {
        try (Connection con = pool.getConnection();
             PreparedStatement pstm = con.prepareStatement(SQLQueries.GET_ALL_NEWSPAPERS_BY_READERID)) {
            pstm.setInt(1, readerID);
            ResultSet newspaperRS=pstm.executeQuery();
            return newspaperMapper.mapNewspapers(newspaperRS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public int save(NewspaperEntity newspaper) {
        return 0;
    }

    @Override
    public void update(NewspaperEntity newspaper) {

    }

    @Override
    public boolean delete(int newspaperId) {
        return false;
    }

    @Override
    public NewspaperEntity get(int newspaperID) {
        NewspaperEntity nPaper=null;
        try (Connection con = pool.getConnection();
             PreparedStatement getType = con.prepareStatement(SQLQueries.GET_NEWSPAPER_BY_ID)) {
            getType.setInt(1, newspaperID);
            ResultSet rs=getType.executeQuery();
            nPaper=newspaperMapper.mapNewspapers(rs).stream().findFirst().orElse(null);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return nPaper;
    }


}

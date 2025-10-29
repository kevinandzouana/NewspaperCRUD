package newspapercrud.dao.jdbc;

import jakarta.inject.Inject;
import newspapercrud.common.Constantes;
import newspapercrud.dao.ReaderRepository;
import newspapercrud.dao.mappers.jdbc_mappers.MapReader;
import newspapercrud.dao.model.ReaderEntity;
import newspapercrud.dao.utilities.DBConnectionPool;
import newspapercrud.dao.utilities.SQLQueries;
import newspapercrud.domain.error.DataBaseError;
import newspapercrud.domain.error.DuplicatedUsernameError;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCReaderRepository implements ReaderRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final DBConnectionPool pool;
    private final MapReader readerMapper;

    @Inject
    public JDBCReaderRepository(DBConnectionPool pool, MapReader readerMapper) {
        this.pool = pool;
        this.readerMapper = readerMapper;
    }

    @Override
    public List<ReaderEntity> getAll() {
        try (Connection con = pool.getConnection();
             Statement getReaders = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ) {
            ResultSet readerRS = getReaders.executeQuery(SQLQueries.GET_ALL_READERS);
            return readerMapper.mapReaders(readerRS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public List<ReaderEntity> getAllByArticle(int articleId) {
        try (Connection con = pool.getConnection();
             PreparedStatement pstm = con.prepareStatement(SQLQueries.GET_ALL_READERS_BY_ARTICLEID)) {
            pstm.setInt(1, articleId);
            ResultSet rs=pstm.executeQuery();
            return readerMapper.mapReaders(rs);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
    }

    @Override
    public ReaderEntity get(int readerID) {
        ReaderEntity reader=null;
        try (Connection con = pool.getConnection();
             PreparedStatement pstm = con.prepareStatement(SQLQueries.GET_READER_BY_ID)) {
            pstm.setInt(1, readerID);
            ResultSet rs=pstm.executeQuery();
            if(rs.next()) {
                reader = readerMapper.mapSingleReader(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
        return reader;
    }

    @Override
    public int save(ReaderEntity reader) {
        try (Connection con = pool.getConnection();
             PreparedStatement insertReader = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertCredential = con.prepareStatement(SQLQueries.INSERT_CREDENTIAL)

        ) {
            try {
            con.setAutoCommit(false);
            insertReader.setString(1, reader.getName());
            insertReader.setDate(2, Date.valueOf(reader.getDob()));
                   insertReader.executeUpdate();
            ResultSet rs = insertReader.getGeneratedKeys();
            rs.next();
            reader.setId(rs.getInt(1));
            insertCredential.setString(1, reader.getCredential().getUserName());
            insertCredential.setString(2, reader.getCredential().getPassword());
            insertCredential.setInt(3, reader.getId());
            insertCredential.executeUpdate();
            con.commit();
            return reader.getId();
            } catch (SQLIntegrityConstraintViolationException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new DuplicatedUsernameError();

            } catch (SQLException e) {
                con.rollback();
                logger.log(Level.SEVERE, e.getMessage(), e);
                throw new DataBaseError(Constantes.DATA_BASE_ERROR);
            } finally {
                con.setAutoCommit(true);
        }} catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }


    }

    @Override
    public void update(ReaderEntity reader) {

    }

    @Override
    public boolean delete(int readerId) {
        //Hacer backup de datos en el futuro
        int result = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement deleteReader = con.prepareStatement(SQLQueries.DELETE_READER);
             PreparedStatement deleteReadArticle = con.prepareStatement(SQLQueries.DELETE_READER_READARTICLES);
             PreparedStatement deleteSubscription = con.prepareStatement(SQLQueries.DELETE_READER_SUBSCRIPTIONS);
             PreparedStatement deleteLogin = con.prepareStatement(SQLQueries.DELETE_READER_LOGIN)

        ) {
            try {
                con.setAutoCommit(false);
                deleteReadArticle.setInt(1, readerId);
                deleteReadArticle.executeUpdate();
                deleteSubscription.setInt(1, readerId);
                deleteSubscription.executeUpdate();
                deleteLogin.setInt(1, readerId);
                deleteLogin.executeUpdate();
                deleteReader.setInt(1, readerId);
                result = deleteReader.executeUpdate();
                con.commit();

            } catch (SQLException e) {
                con.rollback();
                logger.log(Level.SEVERE,e.getMessage(),e);
                throw new DataBaseError(Constantes.DATA_BASE_ERROR);
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE,sqle.getMessage(),sqle);
            throw new DataBaseError(Constantes.DATA_BASE_ERROR);
        }
        return result == 1;
    }

}

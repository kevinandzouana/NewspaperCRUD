package newspapercrud.dao.jdbc;


import newspapercrud.common.Constantes;
import newspapercrud.dao.TypeRepository;
import newspapercrud.dao.mappers.jdbc_mappers.MapTypes;
import newspapercrud.dao.model.TypeEntity;
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
public class JDBCTypeRepository implements TypeRepository {

    private final Logger logger = Logger.getLogger(Constantes.LOGGER);
    private final DBConnectionPool pool;
    private final MapTypes typesMapper;


    public JDBCTypeRepository(DBConnectionPool pool, MapTypes typesMapper) {
        this.pool = pool;
        this.typesMapper = typesMapper;
    }

    @Override
    public List<TypeEntity> getAll() {
        try (Connection con = pool.getConnection();
             Statement getPayments = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
        ) {
            ResultSet typesRS = getPayments.executeQuery(SQLQueries.GET_ALL_TYPES);
            return typesMapper.mapTypes(typesRS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return List.of();
    }

    @Override
    public TypeEntity get(int typeID) {
        TypeEntity type=null;
        try (Connection con = pool.getConnection();
             PreparedStatement getType = con.prepareStatement(SQLQueries.GET_TYPE_BY_ID)) {
            getType.setInt(1, typeID);
            ResultSet rs=getType.executeQuery();
            type=typesMapper.mapTypes(rs).stream().findFirst().orElse(null);
        } catch (SQLException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return type;
    }



    @Override
    public int save(TypeEntity type) {
        return 0;
    }

    @Override
    public void update(TypeEntity type) {

    }

    @Override
    public boolean delete(int typeId, boolean confirmation) {
        return false;
    }


}

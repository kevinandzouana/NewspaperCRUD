package newspapercrud.config;

import newspapercrud.dao.utilities.DBConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DBConnectionPool dbConnectionPool() {
        return new DBConnectionPool();
    }

    @Bean
    @Profile("notInUse")
    public JdbcClient jdbcClient(DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}

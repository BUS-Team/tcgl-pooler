package br.com.db.factory;

import org.postgresql.ds.PGSimpleDataSource;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Map;

public class PostgresDataSourceFactory implements DataSourceFactory {
    private InputStream input;
    private Map<String,String> conf;

    @Override
    public DataSource getDataSource() {
        input = this.getClass().getResourceAsStream("/config.yml");
        conf = (Map<String, String>) new Yaml().load(input);

        PGSimpleDataSource ds = new PGSimpleDataSource();

        ds.setServerName(conf.get("url"));
        ds.setDatabaseName(conf.get("db"));
        ds.setUser(conf.get("user"));
        ds.setPassword(conf.get("password"));

        return ds;
    }
}

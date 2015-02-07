package br.com.db.factory;

import java.io.*;
import java.sql.*;
import java.util.Map;

import br.com.db.dao.*;
import br.com.db.dao.LastUnprocessedPositionProcessedPostgresDAO;
import br.com.db.dao.LastUnprocessedPositionProcessedDAO;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;

public class PostgresFactoryDAO extends FactoryDAO {
    InputStream input;
    Map<String,String> conf;

    private static Connection connection = null;
    private static Connection connectionInsert = null;
    private String url;
    private String user;
    private String password;
    private String driverName;

    public PostgresFactoryDAO() {
        try {
            input = this.getClass().getResourceAsStream("/config.yml");
            conf = (Map<String, String>) new Yaml().load(input);

            url = conf.get("url");
            user = conf.get("user");
            password = conf.get("password");
            driverName = conf.get("driverName");

            Class.forName(driverName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws Exception {
        if (connection == null) {
            return connection = DriverManager.getConnection(url, user, password);
        } else {
            return connection;
        }
    }

    @Override
    public UnprocessedPositionDAO getUnprocessedPositionDAO() throws Exception {
        return new UnprocessedPositionPostgresDAO();
    }

    @Override
    public PositionDAO getPositionDAO() throws Exception {
        return new PositionPostgresDAO();
    }

    @Override
    public LastUnprocessedPositionProcessedDAO getLastUnprocessedPositionDAO() throws Exception {
        return new LastUnprocessedPositionProcessedPostgresDAO();
    }

    @Override
    public Connection getConnectionInsert() throws Exception {
        if (connectionInsert == null) {
            return connectionInsert = DriverManager.getConnection(url, user, password);
        } else {
            return connectionInsert;
        }
    }
}

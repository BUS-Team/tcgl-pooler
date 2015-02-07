package br.com.db.factory;

import br.com.db.DataBaseType;
import br.com.db.dao.LastUnprocessedPositionProcessedDAO;
import br.com.db.dao.PositionDAO;
import br.com.db.dao.UnprocessedPositionDAO;

import java.sql.Connection;

/**
 * Created by breno on 13/12/14.
 */
public abstract class FactoryDAO {

    public abstract LastUnprocessedPositionProcessedDAO getLastUnprocessedPositionDAO() throws Exception;

    public abstract UnprocessedPositionDAO getUnprocessedPositionDAO() throws Exception;

    public abstract PositionDAO getPositionDAO() throws Exception;

    public abstract Connection getConnection() throws Exception;

    public static FactoryDAO getDAOFactory(DataBaseType dataBaseType) {
        switch (dataBaseType) {
            case POSTGRES:
                return new PostgresFactoryDAO();
            case MYSQL:
                return null;
            default:
                return null;
        }
    }

    public abstract Connection getConnectionInsert() throws Exception;
}

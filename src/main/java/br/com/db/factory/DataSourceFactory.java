package br.com.db.factory;

import javax.sql.DataSource;

public interface DataSourceFactory {
    public DataSource getDataSource();
}

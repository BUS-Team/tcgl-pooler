package br.com.db.dao;

import java.sql.SQLException;

/**
 * Created by breno on 14/12/14.
 */
public interface LastUnprocessedPositionProcessedDAO {
    public Long findLastResponsePositionProcessedId() throws SQLException;
}

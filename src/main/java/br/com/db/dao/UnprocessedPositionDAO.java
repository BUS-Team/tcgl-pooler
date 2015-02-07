package br.com.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UnprocessedPositionDAO {
    public void insertResponsePosition(String busLine, String responsePosition) throws SQLException;
    public ResultSet findListBetween(Long minId, Long maxId) throws SQLException;
    public Long findMaxId() throws SQLException;
}

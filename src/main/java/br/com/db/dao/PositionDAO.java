package br.com.db.dao;

import br.com.db.bean.Position;

import java.sql.SQLException;

public interface PositionDAO {
    public void insertPosition(Position p) throws SQLException;
}

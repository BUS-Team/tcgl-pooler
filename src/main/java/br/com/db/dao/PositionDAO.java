package br.com.db.dao;

import br.com.db.bean.Position;

import java.sql.SQLException;

/**
 * Created by breno on 17/12/14.
 */
public interface PositionDAO {
    public void insertPosition(Position p) throws SQLException;
}

package br.com.db.dao;

import br.com.db.bean.Position;
import br.com.db.factory.PostgresFactoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PositionPostgresDAO implements PositionDAO {
    private Connection connetion;
    private PreparedStatement preparedStatement;

    public PositionPostgresDAO() throws Exception {
        connetion = new PostgresFactoryDAO().getConnectionInsert();
    }

    private static String queryInsertPosition =
            "INSERT INTO position(bus_line, bus_direction, date_received, latitude, longitude, type) " +
            "VALUES (?,?,?,?,?,?)";

    @Override
    public void insertPosition(Position p) throws SQLException {
        try {
            connetion.setAutoCommit(false);
            preparedStatement = connetion.prepareStatement(queryInsertPosition);
            preparedStatement.setInt(1, p.getBusLine());
            preparedStatement.setString(2, p.getBusDirection());
            preparedStatement.setTimestamp(3, new Timestamp(p.getDateReceived().getTime()));
            preparedStatement.setFloat(4, p.getLatitude());
            preparedStatement.setFloat(5, p.getLongitude());
            preparedStatement.setInt(6, p.getType());
            preparedStatement.executeUpdate();
            connetion.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

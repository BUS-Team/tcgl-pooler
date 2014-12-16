package br.com.db.dao;

import br.com.db.bean.UnprocessedPosition;
import br.com.db.factory.PostgresFactoryDAO;
import org.joda.time.DateTime;

import java.sql.*;

/**
 * Created by breno on 13/12/14.
 */
public class UnprocessedPositionPostgresDAO implements UnprocessedPositionDAO {

    private Connection connetion;
    private PreparedStatement preparedStatement;

    public UnprocessedPositionPostgresDAO() throws Exception {
        connetion = new PostgresFactoryDAO().getConnection();
    }

    private static String query = "INSERT INTO unprocessed_position(bus_line, date_received, content) VALUES (?, ?, ?)";

    @Override
    public void insertResponsePosition(String busLine, String responseContent) throws SQLException {
        try {
            connetion.setAutoCommit(false);
            preparedStatement = connetion.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(busLine));
            preparedStatement.setTimestamp(2, new Timestamp(new DateTime().getMillis()));
            preparedStatement.setString(3, responseContent);
            preparedStatement.executeUpdate();
            connetion.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String queryfindListBetween = "SELECT * FROM unprocessed_position WHERE id BETWEEN ? AND ?";
    @Override
    public ResultSet findListBetween(Long minId, Long maxId) throws SQLException {
        try {
            connetion.setAutoCommit(false);
            preparedStatement = connetion.prepareStatement(queryfindListBetween);
            preparedStatement.setFetchSize(1000);
            preparedStatement.setLong(1, minId);
            preparedStatement.setLong(2, maxId);

            return  preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String queryFindMaxId = "SELECT MAX(id) AS max_id FROM unprocessed_position";
    @Override
    public Long findMaxId() throws SQLException {
        try {
            preparedStatement = connetion.prepareStatement(queryFindMaxId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null && rs.next()) {
                return rs.getLong("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}

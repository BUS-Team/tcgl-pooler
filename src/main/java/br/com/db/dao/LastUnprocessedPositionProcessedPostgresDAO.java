package br.com.db.dao;

import br.com.db.factory.PostgresFactoryDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by breno on 14/12/14.
 */
public class LastUnprocessedPositionProcessedPostgresDAO implements LastUnprocessedPositionProcessedDAO {

    private Connection connetion;
    private PreparedStatement preparedStatement;

    public LastUnprocessedPositionProcessedPostgresDAO() throws Exception {
        connetion = new PostgresFactoryDAO().getConnection();
    }


    private static String queryFindLastUnprocessedPositionProcessedId = "SELECT MAX(last_id) AS last_id FROM last_unprocessed_position_processed";
    @Override
    public Long findLastResponsePositionProcessedId() throws SQLException {
        ResultSet rs;
        try {
            connetion.setAutoCommit(false);
            preparedStatement = connetion.prepareStatement(queryFindLastUnprocessedPositionProcessedId);
            rs = preparedStatement.executeQuery();

            if (rs != null && rs.next()) {
                return rs.getLong("last_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

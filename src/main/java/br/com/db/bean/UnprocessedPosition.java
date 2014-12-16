package br.com.db.bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by breno on 15/12/14.
 */
public class UnprocessedPosition {
    private Long id;
    private Integer busLine;
    private Date dateReceived;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBusLine() {
        return busLine;
    }

    public void setBusLine(Integer busLine) {
        this.busLine = busLine;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static UnprocessedPosition rowMapper(ResultSet rs) throws SQLException {
        UnprocessedPosition unprocessedPosition = new UnprocessedPosition();
        unprocessedPosition.setId(rs.getLong("id"));
        unprocessedPosition.setBusLine(rs.getInt("bus_line"));
        unprocessedPosition.setDateReceived(rs.getTimestamp("date_received"));
        unprocessedPosition.setContent(rs.getString("content"));
        return unprocessedPosition;
    }

    @Override
    public String toString() {
        return this.id + " " + this.busLine + " " + this.content + " " + this.dateReceived;
    }
}

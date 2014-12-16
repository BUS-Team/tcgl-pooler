package br.com.db.bean;

import java.util.Date;

/**
 * Created by breno on 15/12/14.
 */
public class Position {
    private Long id;
    private Integer busLine;
    private String busDirection;
    private Date dateReceived;
    private Float latitude;
    private Float longitude;

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

    public String getBusDirection() {
        return busDirection;
    }

    public void setBusDirection(String busDirection) {
        this.busDirection = busDirection;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}

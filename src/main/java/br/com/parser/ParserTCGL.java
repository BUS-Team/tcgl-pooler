package br.com.parser;

import br.com.db.bean.Position;
import br.com.db.bean.UnprocessedPosition;
import br.com.db.dao.PositionDAO;
import br.com.db.dao.PositionPostgresDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by breno on 14/12/14.
 */
public class ParserTCGL implements Parser {

    UnprocessedPosition unprocessedPosition;
    List<Position> positions;
    PositionDAO positionDAO;

    @Override
    public void init(ResultSet rs) throws Exception {
        positions = new ArrayList<Position>();
        unprocessedPosition = UnprocessedPosition.rowMapper(rs);
        positionDAO = new PositionPostgresDAO();
    }

    @Override
    public void run() {
        try {

            if (isEmptyPosition(unprocessedPosition)) {
                insertEmptyPosition(unprocessedPosition);
            } else if(isInvalidPosition(unprocessedPosition)) {
                insertInvalidPosition(unprocessedPosition);
            } else {

                String[] parts = unprocessedPosition.getContent().split("&");

                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].contains("TCGL") && (i + 5) < parts.length) {
                        Position p = new Position();
                        p.setBusLine(unprocessedPosition.getBusLine());
                        p.setDateReceived(unprocessedPosition.getDateReceived());

                        if (parts[i + 1].contains("003e")) {
                            p.setBusDirection(parts[i + 1].split("003e")[1]);
                        }

                        p.setLatitude(Float.parseFloat(parts[i + 3].replaceAll("\\s+", "")));
                        p.setLongitude(Float.parseFloat(parts[i + 4].replaceAll("\\s+", "")));
                        p.setType(Integer.parseInt(parts[i + 5].replaceAll("\\s+", "")));

                        i += 6;

                        /*
                        System.out.format("%4s %25s %80s %15s %15s %4s\n",
                                p.getBusLine(),
                                p.getDateReceived(),
                                p.getBusDirection(),
                                p.getLatitude(),
                                p.getLongitude(),
                                p.getType());
                         */

                        positionDAO.insertPosition(p);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertEmptyPosition(UnprocessedPosition unprocessedPosition) throws SQLException {
        Position p = new Position();
        p.setBusLine(unprocessedPosition.getBusLine());
        p.setBusDirection("Empty Position");
        p.setDateReceived(unprocessedPosition.getDateReceived());
        p.setLatitude(0.0F);
        p.setLongitude(0.0F);
        p.setType(-1);
        positionDAO.insertPosition(p);
    }

    public void insertInvalidPosition(UnprocessedPosition unprocessedPosition) throws SQLException {
        Position p = new Position();
        p.setBusLine(unprocessedPosition.getBusLine());
        p.setBusDirection("Invalid Position");
        p.setDateReceived(unprocessedPosition.getDateReceived());
        p.setLatitude(-1.0F);
        p.setLongitude(-1.0F);
        p.setType(-2);
        positionDAO.insertPosition(p);
    }

    public boolean isEmptyPosition(UnprocessedPosition unprocessedPosition) {
        return unprocessedPosition.getContent().contains("{\"cordenadas\":\"\"}");
    }

    public boolean isInvalidPosition(UnprocessedPosition unprocessedPosition) {
        Pattern pattern = Pattern.compile(".+Tentativa.+de leitura quando.+existem dados.+");
        Matcher matcher = pattern.matcher(unprocessedPosition.getContent());
        return matcher.matches();
    }
}

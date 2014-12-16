package br.com.extractor;

import br.com.db.DataBaseType;
import br.com.db.dao.LastUnprocessedPositionProcessedDAO;
import br.com.db.dao.UnprocessedPositionDAO;
import br.com.db.factory.FactoryDAO;
import br.com.parser.Parser;
import br.com.parser.ParserTCGL;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by breno on 14/12/14.
 */
public class PostgresExtractor implements Extractor {

    private Parser parser;
    private UnprocessedPositionDAO unprocessedPositionDAO;
    private LastUnprocessedPositionProcessedDAO lastUnprocessedPositionProcessedDAO;
    private ResultSet rs;
    private Long lastId;
    private Long maxId;

    @Override
    public void run() {
        try {
            Long count = 0L;
            DateTime inicio = new DateTime();
            System.out.println(inicio);

            parser = new ParserTCGL();
            unprocessedPositionDAO = FactoryDAO
                    .getDAOFactory(DataBaseType.POSTGRES)
                    .getResponsePositionDAO();

            lastUnprocessedPositionProcessedDAO = FactoryDAO
                    .getDAOFactory(DataBaseType.POSTGRES)
                    .getLastResponsePositionProcessedDAO();

            maxId = unprocessedPositionDAO.findMaxId();
            lastId = lastUnprocessedPositionProcessedDAO.findLastResponsePositionProcessedId();

            rs = unprocessedPositionDAO.findListBetween(lastId, maxId);

            while (rs != null && !rs.isAfterLast() && rs.next()) {
                Parser p = new ParserTCGL();
                p.init(rs);
                new Thread(p).start();
                count++;
            }

            DateTime fim = new DateTime();
            System.out.println(fim);

            Period d = new Period(inicio, fim);

            PeriodFormatter daysHoursMinutes = new PeriodFormatterBuilder()
                    .appendDays()
                    .appendSuffix(" day", " days")
                    .appendSeparator(" and ")
                    .appendMinutes()
                    .appendSuffix(" minute", " minutes")
                    .appendSeparator(" and ")
                    .appendSeconds()
                    .appendSuffix(" second", " seconds")
                    .toFormatter();

            System.out.println(daysHoursMinutes.print(d));
            System.out.println("rows: " + count);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

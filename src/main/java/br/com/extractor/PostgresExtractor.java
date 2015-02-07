package br.com.extractor;

import br.com.db.DataBaseType;
import br.com.db.dao.LastUnprocessedPositionProcessedDAO;
import br.com.db.dao.UnprocessedPositionDAO;
import br.com.db.factory.FactoryDAO;
import br.com.parser.Parser;
import br.com.parser.ParserTCGL;
import br.com.util.Formatter;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by breno on 14/12/14.
 */
public class PostgresExtractor implements Extractor {

    private ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
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

            unprocessedPositionDAO = FactoryDAO
                    .getDAOFactory(DataBaseType.POSTGRES)
                    .getUnprocessedPositionDAO();

            lastUnprocessedPositionProcessedDAO = FactoryDAO
                    .getDAOFactory(DataBaseType.POSTGRES)
                    .getLastUnprocessedPositionDAO();

            maxId = unprocessedPositionDAO.findMaxId();
            lastId = lastUnprocessedPositionProcessedDAO.findLastResponsePositionProcessedId();

            rs = unprocessedPositionDAO.findListBetween(lastId, maxId);

            while (rs != null && !rs.isAfterLast() && rs.next()) {
                Parser p = new ParserTCGL();
                p.init(rs);
                es.execute(p);
                count++;
            }

            DateTime fim = new DateTime();
            System.out.println(fim);

            Period d = new Period(inicio, fim);

            PeriodFormatter daysHoursMinutes = Formatter.toDaysHoursMinutes();

            System.out.println(daysHoursMinutes.print(d));
            System.out.println("rows: " + count);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

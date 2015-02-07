package br.com.pooler;

import br.com.config.AppConfiguration;
import br.com.db.DataBaseType;
import br.com.db.dao.UnprocessedPositionDAO;
import br.com.db.factory.FactoryDAO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class Pooler implements Runnable {
    private final String USER_AGENT = "Mozilla/5.0";
    private String busLine;
    private UnprocessedPositionDAO dao;

    public Pooler(String busLine) throws Exception {
        this.busLine = busLine;
        dao = FactoryDAO.getDAOFactory(DataBaseType.POSTGRES).getUnprocessedPositionDAO();
    }

    @Override
    public void run() {
        try {
            StringBuffer response = getResponseFromTcgl();
            dao.insertResponsePosition(busLine, response.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuffer getResponseFromTcgl() throws IOException {
        try {
            URL tcgl = new URL(AppConfiguration.siteUrl);
            HttpURLConnection conn = (HttpURLConnection) tcgl.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String urlParameters = "idLinha=" + busLine;

            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

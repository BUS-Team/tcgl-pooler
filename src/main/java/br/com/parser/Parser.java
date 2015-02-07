package br.com.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by breno on 14/12/14.
 */
public interface Parser extends Runnable {
    public void init(ResultSet rs) throws Exception;
}

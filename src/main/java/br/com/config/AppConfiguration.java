package br.com.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class AppConfiguration {
    public static String driverName;
    public static String url;
    public static String user;
    public static String password;
    public static String siteUrl;
    public static String[] busLines;

    public void loadConfigurations() {
        InputStream input = this.getClass().getResourceAsStream("/config.yml");
        Map<String, String> conf = (Map<String, String>) new Yaml().load(input);

        driverName = conf.get("driverName");
        url = conf.get("url");
        user = conf.get("user");
        password = conf.get("password");
        siteUrl = conf.get("siteUrl");
        busLines = conf.get("busLines").split(",");
    }
}

package br.com;

import br.com.config.AppConfiguration;
import br.com.extractor.ExtractorModule;
import br.com.extractor.PostgresExtractor;
import br.com.pooler.PoolerModule;

public class Main {

    public static void main(String[] args) throws Exception {
        new AppConfiguration().loadConfigurations();
        // new PoolerModule();
        // new ExtractorModule();
        new PostgresExtractor().run();
    }
}

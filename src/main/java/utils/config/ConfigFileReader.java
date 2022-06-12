package utils.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader extends Properties {
    private final String CONFIG_PATH = "src/main/resources/config/networkconfig.properties";
    private FileReader reader;

    public ConfigFileReader() {
        initializeReader();
    }

    private void initializeReader() {
        try {
            reader = new FileReader(CONFIG_PATH);
            load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

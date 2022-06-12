package utils.config;

public class ConfigFetcher {
    private static ConfigFetcher fetcher;

    private ConfigFileReader configReader;

    private ConfigFetcher() {
        configReader = new ConfigFileReader();
    }

    private static ConfigFetcher getInstance() {
        if (fetcher == null) fetcher = new ConfigFetcher();
        return fetcher;
    }

    public static String getIp() {
        String potentialIpString = configReader().getProperty("ip");
        if (potentialIpString != null) {
            return potentialIpString;
        }

        return DefaultConfig.IP;
    }

    public static int getPort() {
        String potentialPortString = configReader().getProperty("port");
        if (potentialPortString != null) {
            return Integer.parseInt(potentialPortString);
        }

        return DefaultConfig.PORT;
    }

    private static ConfigFileReader configReader() {
        return getInstance().configReader;
    }
}

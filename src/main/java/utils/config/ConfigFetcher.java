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

    private static ConfigFileReader configReader() {
        return getInstance().configReader;
    }

    public static String fetch(ConfigIdentifier configIdentifier) {
        String configIdentifierString = configIdentifier.toString();
        String configFetchedFromFile = configReader().getProperty(configIdentifierString);
        if (configFetchedFromFile != null) {
            return configFetchedFromFile;
        }

        return fetchFromDefaultConfig(configIdentifier);
    }

    public static String fetchFromDefaultConfig(ConfigIdentifier configIdentifier) {
        switch (configIdentifier) {
            case IP:
                return DefaultConfig.IP;
            case PORT:
                return DefaultConfig.PORT + "";
            case ADD_NEW_PLAYER_IN_NETWORK:
                return DefaultConfig.addNewPlayerInNetwork;
            case TEST_CONNECTION:
                return DefaultConfig.testConnection;
            case ADD_NEW_GAME:
                return DefaultConfig.addNewGame;
            case JOIN_GAME:
                return DefaultConfig.joinGame;
            case ALL_JOINABLE_GAMES:
                return DefaultConfig.allJoinableGames;
            case UPDATE_GAME:
                return DefaultConfig.updateGame;
            case NO_ACTION:
                return DefaultConfig.noAction;
            case MOVE_A_CARD:
                return DefaultConfig.moveACard;
            case PRIVATE_NAME_FOR_PATH:
                return DefaultConfig.privateNameForPath;
            case MAKE_GAME_UNJOINABLE:
                return DefaultConfig.makeGameUnjoinable;
            case SEND_REQUEST:
                return DefaultConfig.sendRequest;
            case HAS_GAME_STARTED:
                return DefaultConfig.hasGameStarted;
            case GET_HOST_ID:
                return DefaultConfig.getHostId;
            case SET_EMOJI:
                return DefaultConfig.setEmoji;
            case CAST_NINJA_CARD:
                return DefaultConfig.castNinjaCard;
            case SHOWED_SMALLEST_CARDS:
                return DefaultConfig.showedSmallestCards;
            default:
                return null;
        }
    }


}

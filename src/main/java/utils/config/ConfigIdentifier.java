package utils.config;

public enum ConfigIdentifier {
    IP("ip"),
    PORT("port"),
    ADD_NEW_PLAYER_IN_NETWORK("addNewPlayerInNetwork"),
    TEST_CONNECTION("testConnection"),
    ADD_NEW_GAME("addNewGame"),
    JOIN_GAME("joinGame"),
    ALL_JOINABLE_GAMES("allJoinableGames"),
    UPDATE_GAME("updateGame"),
    NO_ACTION("noAction"),
    MOVE_A_CARD("moveACard"),
    PRIVATE_NAME_FOR_PATH("privateNameForPath"),
    MAKE_GAME_UNJOINABLE("makeGameUnjoinable"),
    SEND_REQUEST("sendRequest"),
    HAS_GAME_STARTED("hasGameStarted"),
    GET_HOST_ID("getHostId"),
    SET_EMOJI("setEmoji"),
    CAST_NINJA_CARD("castNinjaCard"),
    SHOWED_SMALLEST_CARDS("showedSmallestCards");

    private String identifierString;

    private ConfigIdentifier(String identifierString) {
        this.identifierString = identifierString;
    }

    @Override
    public String toString() {
        return identifierString;
    }
}

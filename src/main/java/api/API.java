package api;

public class API {
    public int addNewPlayer() {
        return 0; // TODO
    }

        public String addNewGame(int numberOfBots, int numberOfHumans, int currentHumanId) {
        return ""; // TODO: returning Gson -> gameWasSuccessfullyCreated, gameId
    }

        public boolean joinGame(int gameId) {
        return true;
        // TODO
    }

        public String getAllGames() {
        // TODO: returning Gson -> gameId, number of free players, number of bots
        // if num of free = 0 => don't return
        return "";
    }

    public String updateGame(int gameId, int currentHumanId) {
        // TODO: updates game and returns Gson of GameState ->
        // level, num of hearts, last card game on ground, num of players, number of cards on ground, hands
        return "";
    }

    public String makeMove(int gameId, int playerId, int cardNumber) {
        // TODO: moveWasValid, doesMoveCauseLossOfHeart
        return "";
    }
}

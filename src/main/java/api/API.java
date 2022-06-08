package api;

public class API {
    public static int addNewPlayer() {
        return 0; // TODO returning id
    }

    public static String addNewGame(int numberOfBots, int currentHumanId) {
        return ""; // TODO: returning Gson -> can make game or not // gameWasSuccessfullyCreated, gameId
    }

    public static boolean joinGame(int gameId) {
        return true;
        // TODO -> can join game or not
    }

    public static String getAllJoinableGames() {
        // TODO: returning Gson -> gameId, number of free players, number of bots
        // if num of free = 0 => don't return
        return "";
    }

    public static String updateGame(int gameId, int currentHumanId) {
        // TODO: updates game and returns Gson of GameState ->
        // isGameStarted , level, num of hearts, last card game on ground, num of players, number of cards on ground, hands
        return "";
    }

    public static String makeMove(int gameId, int playerId, int cardNumber) {
        // TODO: moveWasValid, doesMoveCauseLossOfHeart, smallestCardThatHasCausedLoss (if the second boolean is true)
        return "";
    }

    public static void makeGameUnjoinable(int idGame){
        // TODO
    }
}

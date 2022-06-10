package api;

import api.dataeggs.MakingMoveDataEgg;
import api.dataeggs.joinablegames.JoinableGamesDataEgg;
import api.utils.GsonUtils;
import api.utils.JoinableGamesUtils;
import api.utils.MakingMoveUtils;
import backend.logic.games.Game;
import backend.logic.games.GameManager;

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
        JoinableGamesDataEgg dataEgg = JoinableGamesUtils.getJoinableGamesDataEgg();

        return GsonUtils.getJsonString(dataEgg);
    }

    public static String updateGame(int gameId, int currentHumanId) {
        // TODO: updates game and returns Gson of GameState ->
        // isGameStarted , level  , num of hearts, last card game on ground, num of players, number of cards on ground, hands (my player) , opponents hand meta data (sorted by id)
        //                                                                                                              exm:  60, 17 ,40 ,2 , 0 : 6 , 1 : 7 , 3 : 2
        return "";
    }

    public static String makeMove(int gameId, int playerId, int cardIndex) {
        // TODO: moveWasValid, doesMoveCauseLossOfHeart, smallestCardThatHasCausedLoss (if the second boolean is true)
        Game game = GameManager.getGameById(gameId);
        boolean moveRespectsGroundOrder = MakingMoveUtils.moveRespectsGroundOrder(game, playerId);
        boolean moveCausesLossOfHealth = MakingMoveUtils.moveCausesHealthLoss(game, playerId);
        int smallestCardNumberThatHasCausedLoss = MakingMoveUtils.getSmallestCardInPlayersHands(game);

        // making a move in GameManager:
        MakingMoveUtils.dropCardInGameManager(game, playerId, cardIndex);

        // returning the data-egg:
        MakingMoveDataEgg dataEgg = new MakingMoveDataEgg(moveRespectsGroundOrder, moveCausesLossOfHealth,
                smallestCardNumberThatHasCausedLoss);
        return GsonUtils.getJsonString(dataEgg);
    }

    public static boolean makeGameUnjoinable(int gameId){
        // TODO -> return a boolean that can or not
        return GameManager.gameHasBeenStarted(gameId, true);
    }
}

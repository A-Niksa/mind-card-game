package api;

import api.dataeggs.MakingMoveEgg;
import api.dataeggs.NewGameEgg;
import api.dataeggs.gamestate.GameStateEgg;
import api.dataeggs.joinablegames.JoinableGamesEgg;
import api.dataeggs.ninjarequest.NinjaMoveEgg;
import api.utils.GsonUtils;
import api.utils.JoinableGamesUtils;
import api.utils.MakingMoveUtils;
import api.utils.NinjaRequestUtils;
import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.ninjahandling.CardAndPlayerTuple;
import backend.logic.models.players.Human;

import java.util.ArrayList;

public class API {
    public static int getHostId(int gameId) {
        Game game = GameManager.getGameById(gameId);
        return game.getHostHumanId();
    }

    public static int addNewPlayerToLobby() {
        // returning Id
        Human human = GameManager.createNewHumanInLobby();
        return human.getPlayerId();
    }

    public static String addNewGame(int numberOfBots, int currentHumanId) {
        // returning Gson -> can make game or not // gameWasSuccessfullyCreated, gameId
        Game game = new Game(numberOfBots, currentHumanId);
        NewGameEgg dataEgg = new NewGameEgg(true, game.getGameId());
        GameManager.addGame(game);
        return GsonUtils.getJsonString(dataEgg);
    }

    public static boolean joinGame(int gameId, int playerId) {
        // shouldn't have playerId
        // returns: can join game or not
        boolean canJoinGame = GameManager.canJoinGame(gameId);
        if (canJoinGame) {
            GameManager.joinGame(gameId, playerId);
        }

        return canJoinGame;
    }

    public static String getAllJoinableGames() {
        // returning Gson -> gameId, number of free players, number of bots
        // if num of free = 0 => don't return
        JoinableGamesEgg dataEgg = JoinableGamesUtils.getJoinableGamesDataEgg();

        return GsonUtils.getJsonString(dataEgg);
    }

    public static boolean gameHasStarted(int gameId) {
        Game game = GameManager.getGameById(gameId);
        return game.gameHasBeenStarted();
    }

    public static String getUpdatedGameState(int gameId, int currentHumanId) {
        // TODO: updates game and returns Gson of GameState ->
        // actionHasCausedLoss, smallestLossyCardNumber, hostId, isGameStarted, has there any ninja request, level, num of hearts, last card game on ground, num of players, number of cards on ground, hands (my player) , opponents hand meta data (sorted by id)
        //                                                                                                              exm:  60, 17 ,40 ,2 , 0 : 6 , 1 : 7 , 3 : 2

        Game game = GameManager.getGameById(gameId);
        GameStateEgg dataEgg = new GameStateEgg(game, currentHumanId);

        return GsonUtils.getJsonString(dataEgg);
    }

    public static String useNinjaCard(int gameId) {
        // TODO: moveWasValid, ... ?
        return "";
    }

    public static String makeMove(int gameId, int playerId, int cardIndex) {
        // moveWasValid, doesMoveCauseLossOfHeart, smallestCardThatHasCausedLoss (if the second boolean is true)
        Game game = GameManager.getGameById(gameId);
        boolean moveRespectsGroundOrder = MakingMoveUtils.moveRespectsGroundOrder(game, playerId);
        boolean moveCausesLossOfHealth = MakingMoveUtils.moveCausesHealthLoss(game, playerId);
        int smallestCardNumberThatHasCausedLoss = MakingMoveUtils.getSmallestCardInPlayersHands(game);

        // making a move in GameManager:
        MakingMoveUtils.dropCardInGameManager(game, playerId, cardIndex);

        // returning the data-egg:
        MakingMoveEgg dataEgg = new MakingMoveEgg(moveRespectsGroundOrder, moveCausesLossOfHealth,
                smallestCardNumberThatHasCausedLoss);
        return GsonUtils.getJsonString(dataEgg);
    }

    public static boolean makeGameUnjoinable(int gameId){
        // return a boolean that can or not
        return GameManager.startGameById(gameId);
    }

    public static String useNinjaCard(int gameId, int playerId){
        // welp: should remove playerId
        ArrayList<CardAndPlayerTuple> smallestCardsList = GameManager.dropNinjaCardInGame(gameId);

        NinjaMoveEgg dataEgg = new NinjaMoveEgg(smallestCardsList);
        return GsonUtils.getJsonString(dataEgg);
    }

    public static void sendNinjaRequest(boolean agreesWithRequest, int playerId, int gameId){
        Human human = NinjaRequestUtils.getHumanById(gameId, playerId);
        GameManager.castVoteForNinjaRequestInGame(gameId, human, agreesWithRequest);
    }
}
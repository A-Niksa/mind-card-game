package utils.jsonparsing.literals.dataeggs.gamestate;

import backend.logic.games.Game;
import backend.logic.games.components.Hand;
import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;
import utils.jsonparsing.literals.utils.GameStateUtils;

import java.util.List;

public class GameStateEgg extends DataEgg {
    private boolean gameHasStarted;
    private int currentRound;
    private int numberOfHealthCards;
    private int lastCardNumberOnGround;
    private int numberOfPlayers;
    private int numberOfCardsOnGround;
    private Hand handOfCurrentPlayer;
    private List<HandDataEgg> handsOfOtherPlayersList;

    public GameStateEgg(Game game, int playerId) {
        super(DataEggType.GAME_STATE_EGG);

        gameHasStarted = game.gameHasBeenStarted();
        currentRound = game.getCurrentRound();
        numberOfHealthCards = game.getDeck().getNumberOfHealthCards();
        numberOfPlayers = game.getNumberOfPlayers();

        lastCardNumberOnGround = game.getDroppingGround()
                .peek().getCardNumber();
        numberOfCardsOnGround = game.getDroppingGround()
                .getNumberOfCardsOnGround();

        handOfCurrentPlayer = GameStateUtils.getHandById(game, playerId);
        handsOfOtherPlayersList = GameStateUtils.getHandsOfPlayersOtherThanCurrentById(game, playerId);
    }

    public boolean isGameHasStarted() {
        return gameHasStarted;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getNumberOfHealthCards() {
        return numberOfHealthCards;
    }

    public int getLastCardNumberOnGround() {
        return lastCardNumberOnGround;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfCardsOnGround() {
        return numberOfCardsOnGround;
    }

    public Hand getHandOfCurrentPlayer() {
        return handOfCurrentPlayer;
    }

    public List<HandDataEgg> getHandsOfOtherPlayersList() {
        return handsOfOtherPlayersList;
    }
}

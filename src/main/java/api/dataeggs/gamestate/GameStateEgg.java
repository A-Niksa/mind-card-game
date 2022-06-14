package api.dataeggs.gamestate;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;
import api.dataeggs.ninjarequest.NinjaRequestStatus;
import api.utils.GameStateUtils;
import backend.logic.games.Game;
import backend.logic.games.components.Hand;

import java.util.List;

public class GameStateEgg extends DataEgg {
    private int hostId;
    private boolean gameHasStarted;
    private int currentRound;
    private int numberOfHealthCards;
    private int lastCardNumberOnGround;
    private int numberOfPlayers;
    private int numberOfCardsOnGround;
    private Hand handOfCurrentPlayer;
    private List<HandEgg> handsOfOtherPlayersList;
    private boolean thereHasBeenANinjaRequest;
    private NinjaRequestStatus ninjaRequestStatus;
    private boolean latestActionHasCausedLoss;
    private int smallestCardNumberThatHasCausedLoss;

    public GameStateEgg(Game game, int playerId) {
        super(DataEggType.GAME_STATE_EGG);

        hostId = game.getHostHumanId();
        gameHasStarted = game.gameHasBeenStarted();
        currentRound = game.getCurrentRound();
        numberOfHealthCards = game.getDeck().getNumberOfHealthCards();
        numberOfPlayers = game.getNumberOfPlayers();

        if(game.getDroppingGround() == null){
            lastCardNumberOnGround = -1;
        }

        if(game.getDroppingGround().getNumberOfCardsOnGround() == 0){
            lastCardNumberOnGround = -1;
        }

        else{
            lastCardNumberOnGround = game.getDroppingGround()
                    .peek().getCardNumber();
        }

        numberOfCardsOnGround = game.getDroppingGround()
                .getNumberOfCardsOnGround();

        handOfCurrentPlayer = GameStateUtils.getHandById(game, playerId);
        handsOfOtherPlayersList = GameStateUtils.getHandsOfPlayersOtherThanCurrentById(game, playerId);

        thereHasBeenANinjaRequest = GameStateUtils.thereHasBeenANinjaRequestInGame(game.getGameId());
        ninjaRequestStatus = GameStateUtils.getNinjaRequestStatus(game.getGameId());

        latestActionHasCausedLoss = game.getActionLogger().latestActionHasCausedLoss();
        smallestCardNumberThatHasCausedLoss = game.getActionLogger().getSmallestCardNumberThatCausedLoss();
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
        handOfCurrentPlayer.sortHand();
        return handOfCurrentPlayer;
    }

    public List<HandEgg> getHandsOfOtherPlayersList() {
        return handsOfOtherPlayersList;
    }

    public boolean isThereHasBeenANinjaRequest() {
        return thereHasBeenANinjaRequest;
    }

    public NinjaRequestStatus getNinjaRequestStatus() {
        return ninjaRequestStatus;
    }

    public int getHostId() {
        return hostId;
    }

    public boolean latestActionHasCausedLoss() {
        return latestActionHasCausedLoss;
    }

    public int getSmallestCardNumberThatHasCausedLoss() {
        return smallestCardNumberThatHasCausedLoss;
    }
}

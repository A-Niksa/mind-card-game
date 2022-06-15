package utils.jsonparsing.literals.dataeggs.gamestate;

import backend.logic.games.Game;
import backend.logic.games.components.Hand;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;
import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;
import utils.jsonparsing.literals.dataeggs.comparators.EmojiEggComparator;
import utils.jsonparsing.literals.dataeggs.comparators.HandEggComparator;
import utils.jsonparsing.literals.utils.GameStateUtils;

import java.util.ArrayList;
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
    private List<NinjaRequestEgg> ninjaRequestsList;
    private boolean latestActionHasCausedLoss;
    private int smallestCardNumberThatHasCausedLoss;
    private int playerIdOfLatestAction;
    private List<EmojiEgg> playerEmojisList;
    private EmojiEgg emojiEggOfCurrentPlayer;

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

        latestActionHasCausedLoss = game.getActionLogger().latestActionHasCausedLoss();
        smallestCardNumberThatHasCausedLoss = game.getActionLogger().getSmallestCardNumberThatCausedLoss();
        playerIdOfLatestAction = game.getActionLogger().getPlayerIdOfLatestAction();
    }

    public boolean gameHasStarted() {
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
        handsOfOtherPlayersList.sort(new HandEggComparator());
        return handsOfOtherPlayersList;
    }

    public boolean thereHasBeenANinjaRequest() {
        return thereHasBeenANinjaRequest;
    }

    public List<NinjaRequestEgg> getNinjaRequestsList() {
        return ninjaRequestsList;
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

    public int getPlayerIdOfLatestAction() {
        return playerIdOfLatestAction;
    }

    public List<EmojiEgg> getPlayerEmojisList() {
        playerEmojisList.sort(new EmojiEggComparator());
        return playerEmojisList;
    }

    public EmojiEgg getEmojiEggOfCurrentPlayer() {
        return emojiEggOfCurrentPlayer;
    }
}

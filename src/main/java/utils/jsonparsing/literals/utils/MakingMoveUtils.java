package utils.jsonparsing.literals.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.JudgeUtils;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class MakingMoveUtils {
    public static void dropCardInGameManager(Game game, int playerId, int cardIndex) {
        NumberedCard card = getCardAndRemoveByIndex(game, playerId, cardIndex);
        GameManager.dropCardInGame(game.getGameId(), playerId, card);
    }

    private static void dropNinjaCardInGameManager(Game game, int playerId, int cardIndex) {

    }

    private static NumberedCard getCardAndRemoveByIndex(Game game, int playerId, int cardIndex) {
        Player player = getPlayerById(game, playerId);
        player.getHand().removeCard(cardIndex);
        return getCardByIndex(player, cardIndex);
    }

    private static NumberedCard getCardByIndex(Player player, int cardIndex) {
        List<NumberedCard> cardsList = player.getHand().getNumberedCardsList();
        cardsList.sort(new NumberedCardComparator());
        return cardsList.get(cardIndex);
    }

    public static int getSmallestCardInPlayersHands(Game game) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = JudgeUtils.getCardsOfAllPlayersList(game);
        cardsOfAllPlayersList.sort(new NumberedCardComparator());

        return cardsOfAllPlayersList.get(0).getCardNumber();
    }

    public static boolean moveCausesHealthLoss(Game game, int playerId, int cardIndex) {
        if (cardIndex == -1) { // ninja card
            return true;
        }

        int cardNumber = getCardNumberByIndex(game, playerId);
        return !JudgeUtils.cardToDropIsTheMinOfAllPlayers(game, cardNumber);
    }

    public static boolean moveRespectsGroundOrder(Game game, int playerId, int cardIndex) {
        if (cardIndex == -1) { // ninja card
            return true;
        }

        int cardNumber = getCardNumberByIndex(game, playerId);
        int topCardNumber = getTopCardNumber(game);

        return topCardNumber < cardNumber;
    }

    private static int getTopCardNumber(Game game) {
        NumberedCard topCard = game.getDroppingGround().peek();
        return topCard.getCardNumber();
    }

    private static int getCardNumberByIndex(Game game, int playerId) {
        List<NumberedCard> cardsOfPlayerList = getPlayerById(game, playerId)
                .getHand().getNumberedCardsList();
        cardsOfPlayerList.sort(new NumberedCardComparator());
        return cardsOfPlayerList.get(0).getCardNumber();
    }

    private static Player getPlayerById(Game game, int playerId) {
        for (Player player : game.getPlayersList()) {
            if (playerId == player.getPlayerId()) {
                return player;
            }
        }

        return null;
    }
}

package api.utils;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.JudgeUtils;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class MakingMoveUtils {
    public static int getSmallestCardInPlayersHands(Game game) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = JudgeUtils.getCardsOfAllPlayersList(game);
        cardsOfAllPlayersList.sort(new NumberedCardComparator());

        return cardsOfAllPlayersList.get(0).getCardNumber();
    }

    public static boolean moveCausesHealthLoss(Game game, int playerId, int cardIndex) {
        int cardNumber = getCardNumberByIndex(game, playerId, cardIndex);
        return !JudgeUtils.cardToDropIsTheMinOfAllPlayers(game, cardNumber);
    }

    public static boolean moveRespectsGroundOrder(Game game, int playerId, int cardIndex) {
        int cardNumber = getCardNumberByIndex(game, playerId, cardIndex);
        int topCardNumber = getTopCardNumber(game);

        return topCardNumber < cardNumber;
    }

    private static int getTopCardNumber(Game game) {
        NumberedCard topCard = game.getDroppingGround().peek();
        return topCard.getCardNumber();
    }

    private static int getCardNumberByIndex(Game game, int playerId, int cardIndex) {
        List<NumberedCard> cardsOfPlayerList = getCardsOfPlayerList(game, playerId);
        cardsOfPlayerList.sort(new NumberedCardComparator());
        return cardsOfPlayerList.get(0).getCardNumber();
    }

    private static List<NumberedCard> getCardsOfPlayerList(Game game, int playerId) {
        for (Player player : game.getPlayersList()) {
            if (playerId == player.getPlayerId()) {
                return player.getHand().getNumberedCardsList();
            }
        }

        return null;
    }
}

package backend.logic.games.components;

import backend.logic.games.Game;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class JudgeUtils {
    public static synchronized void scorchSmallerCards(Game game, NumberedCard droppedCard) {
        ArrayList<Hand> handsList = getHandsOfAllPlayers(game);
        for (Hand hand : handsList) {
            for (NumberedCard card : hand.getNumberedCardsList()) {
                if (card.getCardNumber() < droppedCard.getCardNumber()) {
                    hand.removeCard(card);
                }
            }
        }
    }

    private static ArrayList<Hand> getHandsOfAllPlayers(Game game) {
        List<Player> playersList = game.getPlayersList();

        ArrayList<Hand> handsList = new ArrayList<>();
        for (Player player : playersList) {
            handsList.add(player.getHand());
        }
        return handsList;
    }

    public static boolean allCardsHaveBeenDropped(Game game) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = getCardsOfAllPlayersList(game);
        return cardsOfAllPlayersList.size() < 1;
    }

    public static boolean checkIfPlayersHaveLost(Game game) {
        Deck deck = game.getDeck();
        if (deck.getNumberOfHealthCards() < 1) {
            game.setGameHasEnded(true);
            game.setGameHasResultedInLoss(true);

            return true;
        }

        return false;
    }

    public static boolean cardToDropIsTheMinOfAllPlayers(Game game, int cardNumber) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = getCardsOfAllPlayersList(game);

        NumberedCardComparator comparator = new NumberedCardComparator();
        cardsOfAllPlayersList.sort(comparator);

        return cardsOfAllPlayersList.get(0).getCardNumber() == cardNumber;
    }

    public static boolean cardToDropIsTheMinOfAllPlayers(Game game, NumberedCard cardToDrop) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = getCardsOfAllPlayersList(game);

        NumberedCardComparator comparator = new NumberedCardComparator();
        cardsOfAllPlayersList.sort(comparator);

        return cardsOfAllPlayersList.get(0).getCardNumber() == cardToDrop.getCardNumber();
    }

    public static ArrayList<NumberedCard> getCardsOfAllPlayersList(Game game) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = new ArrayList<>();
        List<NumberedCard> cardsOfEachPlayerList;
        for (Player player : game.getPlayersList()) {
            cardsOfEachPlayerList = player.getHand().getNumberedCardsList();
            cardsOfAllPlayersList.addAll(cardsOfEachPlayerList);
        }

        return cardsOfAllPlayersList;
    }
}

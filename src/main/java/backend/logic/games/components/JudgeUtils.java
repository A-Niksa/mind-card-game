package backend.logic.games.components;

import backend.logic.games.Game;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class JudgeUtils {
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

    public static boolean cardToDropIsTheMinOfAllPlayers(Game game, NumberedCard cardToDrop) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = getCardsOfAllPlayersList(game);

        NumberedCardComparator comparator = new NumberedCardComparator();
        cardsOfAllPlayersList.sort(comparator);

        return cardsOfAllPlayersList.get(0).getCardNumber() == cardToDrop.getCardNumber();
    }

    private static ArrayList<NumberedCard> getCardsOfAllPlayersList(Game game) {
        ArrayList<NumberedCard> cardsOfAllPlayersList = new ArrayList<>();
        List<NumberedCard> cardsOfEachPlayerList = new ArrayList<>();
        for (Player player : game.getPlayersList()) {
            cardsOfEachPlayerList = player.getHand().getNumberedCardsList();
            cardsOfAllPlayersList.addAll(cardsOfEachPlayerList);
        }

        return cardsOfAllPlayersList;
    }
}

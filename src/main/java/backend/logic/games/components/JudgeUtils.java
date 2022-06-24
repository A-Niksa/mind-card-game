package backend.logic.games.components;

import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class JudgeUtils {
    public static synchronized void scorchSmallerCards(Game game, NumberedCard droppedCard) {
        ArrayList<Hand> handsList = getHandsOfAllPlayers(game);
        for (Hand hand : handsList) {
            for (int i = 0; i < hand.getNumberedCardsList().size(); i++) {
                if (hand.getNumberedCardsList().get(i).getCardNumber() < droppedCard.getCardNumber()) {
                    hand.removeCard(hand.getNumberedCardsList().get(i));
                    i--;
                }
            }
        }
    }

    public static int getIdOfOnlyPlayerWithCards(int gameId) { // returns -1 if more than 1 player has at least a card
        Game game = GameManager.getGameById(gameId);
        List<Player> playersList = game.getPlayersList();

        int numberOfPlayersWithCards = 0;
        int idOfOnlyPlayerWithCards = -1;
        Hand hand;
        for (Player player : playersList) {
            hand = player.getHand();
            if (hand.getNumberedCardsList().size() > 0) {
                numberOfPlayersWithCards++;
                idOfOnlyPlayerWithCards = player.getPlayerId();
            }
        }

        return numberOfPlayersWithCards > 1 ? -1 : idOfOnlyPlayerWithCards;
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

        if(cardsOfAllPlayersList.size() == 0){
            return true;
        }
        return cardsOfAllPlayersList.get(0).getCardNumber() > cardToDrop.getCardNumber();
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

package backend.logic.models.players.bots;

import backend.logic.games.components.Deck;
import backend.logic.models.players.Player;

import java.util.ArrayList;
import java.util.List;

public class BotGenerationUtils {
    public static void giveHandsToPlayersFromDeck(List<Player> playersList, Deck deck, int numberOfCardsPerHand) {
        for (Player player : playersList) {
            player.setHand(deck.getRandomHand(numberOfCardsPerHand));
        }
    }

    public static ArrayList<Bot> getSomeBots(int numberOfRequestedBots, Deck deck, int numberOfCardsPerHand,
                                             int currentGameId) {
        ArrayList<Bot> requestedBotsList = new ArrayList<>();

        switch (numberOfRequestedBots) {
            // did not add break on purpose:
            case 3:
                ThirdBot thirdBot = new ThirdBot(currentGameId);
                requestedBotsList.add(thirdBot);
            case 2:
                SecondBot secondBot = new SecondBot(currentGameId);
                requestedBotsList.add(secondBot);
            case 1:
                FirstBot firstBot = new FirstBot(currentGameId);
                requestedBotsList.add(firstBot);
        }

        return requestedBotsList;
    }
}

package backend.logic.models.players.bots;

import backend.logic.games.components.Deck;

import java.util.ArrayList;

public class BotGenerationUtils {
    public static ArrayList<Bot> getSomeBots(int numberOfRequestedBots, Deck deck, int numberOfCardsPerHand) {
        ArrayList<Bot> requestedBotsList = new ArrayList<Bot>();

        switch (numberOfRequestedBots) {
            // did not add break on purpose:
            case 3:
                ThirdBot thirdBot = new ThirdBot(deck.getRandomHand(numberOfCardsPerHand));
                requestedBotsList.add(thirdBot);
            case 2:
                SecondBot secondBot = new SecondBot(deck.getRandomHand(numberOfCardsPerHand));
                requestedBotsList.add(secondBot);
            case 1:
                FirstBot firstBot = new FirstBot(deck.getRandomHand(numberOfCardsPerHand));
                requestedBotsList.add(firstBot);
        }

        return requestedBotsList;
    }
}

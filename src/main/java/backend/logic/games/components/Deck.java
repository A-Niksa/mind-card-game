package backend.logic.games.components;

import backend.logic.models.cards.HealthCard;
import backend.logic.models.cards.NinjaCard;
import backend.logic.models.cards.NumberedCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    public static final int NUMBER_OF_NUMBERED_CARDS = 100;
    public static final int NUMBER_OF_NINJA_CARDS = 2;

    private int numberOfPlayers;
    private List<NumberedCard> numberedCardsList;
    private List<NinjaCard> ninjaCardsList;
    private List<HealthCard> healthCardsList;
    private Random randomGenerator;

    public Deck(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

        initializeCardsLists();
        addCardsToLists();

        randomGenerator = new Random();
    }

    private void initializeCardsLists() {
        numberedCardsList = new ArrayList<>();
        ninjaCardsList = new ArrayList<>();
        healthCardsList = new ArrayList<>();
    }

    private void addCardsToLists() {
        addNumberedCards();
        addNinjaCards();
        addHealthCards();
    }

    public void resetDeck() {
        numberedCardsList.clear();
        addNumberedCards();
    }

    private void addNumberedCards() {
        for (int i = 0; i < NUMBER_OF_NUMBERED_CARDS; i++) {
            numberedCardsList.add(new NumberedCard(i+1));
        }
        Collections.shuffle(numberedCardsList);
    }

    private void addNinjaCards() {
        for (int i = 0; i < NUMBER_OF_NINJA_CARDS; i++) {
            ninjaCardsList.add(new NinjaCard());
        }
    }

    private void addHealthCards() {
        for (int i = 0; i < numberOfPlayers; i++) {
            healthCardsList.add(new HealthCard());
        }
    }

    public void removeOneHealthCard() {
        if (healthCardsList.size() >= 1) {
            healthCardsList.remove(0);
        }
    }

    public Hand getRandomHand(int sizeOfHand) {
        ArrayList<NumberedCard> randomNumberedCardsList = new ArrayList<>();
        randomNumberedCardsList.add(getRandomNumberedCard());
        randomNumberedCardsList.add(getRandomNumberedCard());

        return new Hand(randomNumberedCardsList);
    }

    private NumberedCard getRandomNumberedCard() {
        int randomIndex = randomGenerator.nextInt(numberedCardsList.size());
        return numberedCardsList.remove(randomIndex);
    }

    public int getNumberOfNumberedCards() {
        return numberedCardsList.size();
    }

    public int getNumberOfHealthCards() {
        return healthCardsList.size();
    }

    public List<HealthCard> getHealthCardsList() {
        return healthCardsList;
    }
}
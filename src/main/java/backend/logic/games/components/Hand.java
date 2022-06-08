package backend.logic.games.components;

import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private int playerId;
    private List<NumberedCard> numberedCardsList;
    private NumberedCardComparator comparator;

    public Hand(List<NumberedCard> numberedCardsList) {
        this.numberedCardsList = numberedCardsList;
    }

    public Hand() {
        numberedCardsList = new ArrayList<>();
        comparator = new NumberedCardComparator();
    }

    public void addCard(NumberedCard card) {
        numberedCardsList.add(card);
    }

    public NumberedCard getSmallestCard() { // removes the smallest card from the hand as well
        if (numberedCardsList.isEmpty()) {
            return null;
        }

        numberedCardsList.sort(comparator);
        return numberedCardsList.remove(0);
    }

    public boolean hasAnyCards() {
        return !numberedCardsList.isEmpty();
    }

    public List<NumberedCard> getNumberedCardsList() {
        return numberedCardsList;
    }
}

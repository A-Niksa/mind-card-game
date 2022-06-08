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

    public NumberedCard getSmallestCard() {
        if (numberedCardsList.isEmpty()) {
            return null;
        }

        numberedCardsList.sort(comparator);
        return numberedCardsList.get(0);
    }

    public List<NumberedCard> getNumberedCardsList() {
        return numberedCardsList;
    }
}

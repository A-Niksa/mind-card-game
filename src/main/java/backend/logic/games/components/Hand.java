package backend.logic.games.components;

import backend.logic.models.cards.NumberedCard;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private int playerId;
    private List<NumberedCard> numberedCardsList;

    public Hand(List<NumberedCard> numberedCardsList) {
        this.numberedCardsList = numberedCardsList;
    }

    public Hand() {
        numberedCardsList = new ArrayList<>();
    }

    public List<NumberedCard> getNumberedCardsList() {
        return numberedCardsList;
    }
}

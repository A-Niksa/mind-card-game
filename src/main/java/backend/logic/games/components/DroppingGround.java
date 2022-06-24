package backend.logic.games.components;

import backend.logic.models.cards.NumberedCard;

import java.util.ArrayDeque;
import java.util.Deque;

public class DroppingGround {
    private Deque<NumberedCard> droppedCardsStack;

    public DroppingGround() {
        droppedCardsStack = new ArrayDeque<>();
    }

    public void dropCardOnGround(NumberedCard card) {
        droppedCardsStack.push(card);
    }

    public NumberedCard peek() {
        return droppedCardsStack.peek();
    }

    public void clear() {
        droppedCardsStack.clear();
    }

    public int getNumberOfCardsOnGround() {
        return droppedCardsStack.size();
    }
}

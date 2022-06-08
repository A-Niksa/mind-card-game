package backend.logic.games.components;

import backend.logic.models.cards.Card;
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

    public boolean cardToDropRespectsOrder(NumberedCard cardToDrop) {
        NumberedCard cardOnTop = droppedCardsStack.peek();
        if (cardOnTop == null) {
            return true; // there's no card for which the order has to be respected!
        }

        return cardToDrop.getCardNumber() > cardOnTop.getCardNumber();
    }

    public void clear() {
        droppedCardsStack.clear();
    }
}

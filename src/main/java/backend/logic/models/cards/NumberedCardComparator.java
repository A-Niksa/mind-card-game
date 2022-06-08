package backend.logic.models.cards;

import java.util.Comparator;

public class NumberedCardComparator implements Comparator<NumberedCard> {
    @Override
    public int compare(NumberedCard firstNumberedCard, NumberedCard secondNumberedCard) {
        return firstNumberedCard.getCardNumber() - secondNumberedCard.getCardNumber();
    }
}

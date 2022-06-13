package backend.logic.models.cards;

public class NumberedCard extends Card implements Comparable<NumberedCard> {
    private int cardNumber;

    public NumberedCard(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    @Override
    public int compareTo(NumberedCard numberedCard) {
        return cardNumber - numberedCard.getCardNumber();
    }
}

package backend.logic.models.cards;

public class NumberedCard extends Card {
    private int cardNumber;

    public NumberedCard(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }
}

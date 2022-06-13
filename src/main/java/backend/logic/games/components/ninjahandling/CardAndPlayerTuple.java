package backend.logic.games.components.ninjahandling;

import backend.logic.models.cards.NumberedCard;

public class CardAndPlayerTuple implements Comparable<CardAndPlayerTuple> {
    private NumberedCard card;
    private int playerId;

    public CardAndPlayerTuple(NumberedCard card, int playerId) {
        this.card = card;
        this.playerId = playerId;
    }

    public NumberedCard getCard() {
        return card;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int compareTo(CardAndPlayerTuple cardAndPlayerTuple) {
        return card.getCardNumber() - cardAndPlayerTuple.getCard().getCardNumber();
    }
}

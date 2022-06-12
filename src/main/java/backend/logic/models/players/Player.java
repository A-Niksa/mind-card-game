package backend.logic.models.players;

import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;

public abstract class Player {
    private static int globalPlayerId = 0;

    protected Hand hand;
    protected int playerId;
    protected boolean isBot;

    protected Player(Hand hand, boolean isBot) {
        this.hand = hand;
        this.isBot = isBot;

        playerId = globalPlayerId;
        globalPlayerId++;
    }

    public void getBackRejectedCard(NumberedCard card) {
        hand.addCard(card);
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isBot() {
        return isBot;
    }
}

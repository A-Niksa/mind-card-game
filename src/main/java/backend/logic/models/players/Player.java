package backend.logic.models.players;

import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;

public abstract class Player {
    private static int globalPlayerId = 0;

    protected Hand hand;
    protected int playerId;

    protected Player(Hand hand) {
        this.hand = hand;

        playerId = globalPlayerId;
        globalPlayerId++;
    }

    public void getBackRejectedCard(NumberedCard card) {
        hand.addCard(card);
    }

    public int getPlayerId() {
        return playerId;
    }
}

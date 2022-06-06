package logic.models.players;

import logic.games.components.Hand;

public abstract class Player {
    private Hand hand;

    protected Player(Hand hand) {
        this.hand = hand;
    }
}

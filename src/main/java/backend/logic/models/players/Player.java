package backend.logic.models.players;

import backend.logic.games.components.Hand;

public abstract class Player {
    private static int globalPlayerId = 0;

    protected Hand hand;
    protected int playerId;

    protected Player(Hand hand) {
        this.hand = hand;

        playerId = globalPlayerId;
        globalPlayerId++;
    }
}

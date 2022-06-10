package api.dataeggs.gamestate;

import backend.logic.games.components.Hand;

public class HandDataEgg {
    private int playerId;
    private Hand playerHand;

    public HandDataEgg(int playerId, Hand playerHand) {
        this.playerId = playerId;
        this.playerHand = playerHand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }
}
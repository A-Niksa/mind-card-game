package api.dataeggs.gamestate;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;
import backend.logic.games.components.Hand;

public class HandDataEgg extends DataEgg {
    private int playerId;
    private Hand playerHand;

    public HandDataEgg(int playerId, Hand playerHand) {
        super(DataEggType.HAND_EGG); // should it extend DataEgg?

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
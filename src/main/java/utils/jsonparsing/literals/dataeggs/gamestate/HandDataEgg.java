package utils.jsonparsing.literals.dataeggs.gamestate;

import backend.logic.games.components.Hand;
import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;

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
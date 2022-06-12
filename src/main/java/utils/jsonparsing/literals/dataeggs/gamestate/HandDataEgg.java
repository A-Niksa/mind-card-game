package utils.jsonparsing.literals.dataeggs.gamestate;

import backend.logic.games.components.Hand;
import utils.jsonparsing.literals.dataeggs.DataEgg;

public class HandDataEgg extends DataEgg {
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
package utils.jsonparsing.literals.dataeggs.gamestate;

import backend.logic.games.components.Hand;
import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;

public class HandEgg extends DataEgg implements Comparable<HandEgg> {
    private int playerId;
    private Hand playerHand;

    public HandEgg(int playerId, Hand playerHand) {
        super(DataEggType.HAND_EGG); // should it extend DataEgg?

        this.playerId = playerId;
        this.playerHand = playerHand;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Hand getPlayerHand() {
        playerHand.sortHand();
        return playerHand;
    }

    @Override
    public int compareTo(HandEgg handEgg) {
        return playerId - handEgg.getPlayerId();
    }
}
package api.dataeggs.gamestate;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;
import backend.logic.games.components.Hand;

public class HandEgg extends DataEgg implements Comparable<HandEgg> {
    private int playerId;
    private Hand playerHand;

    public HandEgg(int playerId, Hand playerHand) {
        super(DataEggType.HAND_EGG);

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
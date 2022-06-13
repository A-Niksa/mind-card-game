package api.dataeggs.ninjarequest;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;

public class DroppedCardEgg extends DataEgg implements Comparable<DroppedCardEgg> {
    private int playerId;
    private int cardNumber;

    public DroppedCardEgg(int playerId, int cardNumber) {
        super(DataEggType.DROPPED_CARD_EGG);

        this.playerId = playerId;
        this.cardNumber = cardNumber;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    @Override
    public int compareTo(DroppedCardEgg droppedCardEgg) {
        return cardNumber - droppedCardEgg.getCardNumber();
    }
}

package utils.jsonparsing.literals.dataeggs.ninjarequest;

import utils.jsonparsing.literals.dataeggs.DataEgg;
import utils.jsonparsing.literals.dataeggs.DataEggType;

public class DroppedCardEgg extends DataEgg {
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
}
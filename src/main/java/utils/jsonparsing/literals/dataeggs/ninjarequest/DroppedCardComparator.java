package utils.jsonparsing.literals.dataeggs.ninjarequest;

import java.util.Comparator;

public class DroppedCardComparator implements Comparator<DroppedCardEgg> {
    @Override
    public int compare(DroppedCardEgg firstDroppedCardEgg, DroppedCardEgg secondDroppedCardEgg) {
        return firstDroppedCardEgg.getCardNumber() - secondDroppedCardEgg.getCardNumber();
    }
}

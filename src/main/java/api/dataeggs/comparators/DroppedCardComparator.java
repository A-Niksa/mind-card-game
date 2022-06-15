package api.dataeggs.comparators;

import api.dataeggs.ninjarequest.DroppedCardEgg;

import java.util.Comparator;

public class DroppedCardComparator implements Comparator<DroppedCardEgg> {
    @Override
    public int compare(DroppedCardEgg firstDroppedCardEgg, DroppedCardEgg secondDroppedCardEgg) {
        return firstDroppedCardEgg.getCardNumber() - secondDroppedCardEgg.getCardNumber();
    }
}

package api.dataeggs.comparators;

import api.dataeggs.gamestate.HandEgg;

import java.util.Comparator;

public class HandEggComparator implements Comparator<HandEgg> {
    @Override
    public int compare(HandEgg firstHandEgg, HandEgg secondHandEgg) {
        return firstHandEgg.getPlayerId() - secondHandEgg.getPlayerId();
    }
}

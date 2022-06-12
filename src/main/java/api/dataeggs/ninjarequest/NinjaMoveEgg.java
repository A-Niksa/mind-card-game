package api.dataeggs.ninjarequest;

import api.dataeggs.gamestate.HandEgg;
import backend.logic.games.components.ninjahandling.CardAndPlayerTuple;

import java.util.ArrayList;
import java.util.List;

public class NinjaMoveEgg {
    private List<DroppedCardEgg> droppedCardsList;
    private int smallestCardNumber;

    public NinjaMoveEgg(List<CardAndPlayerTuple> smallestCardsList) {
        initializeAndSetDroppedCardsList(smallestCardsList);
        initializeSmallestCardNumber();
    }

    private void initializeAndSetDroppedCardsList(List<CardAndPlayerTuple> smallestCardsList) {
        droppedCardsList = new ArrayList<>();
        for (CardAndPlayerTuple cardTuple : smallestCardsList) {
            droppedCardsList.add(new DroppedCardEgg(cardTuple.getPlayerId(),
                    cardTuple.getCard().getCardNumber()));
        }
    }

    private void initializeSmallestCardNumber() {
        droppedCardsList.sort(new DroppedCardComparator());
        smallestCardNumber = droppedCardsList.get(0).getCardNumber();
    }

    public int getSmallestCardNumber() {
        return smallestCardNumber;
    }

    public List<DroppedCardEgg> getDroppedCardsList() {
        return droppedCardsList;
    }
}

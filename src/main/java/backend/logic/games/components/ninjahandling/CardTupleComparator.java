package backend.logic.games.components.ninjahandling;

import java.util.Comparator;

public class CardTupleComparator implements Comparator<CardAndPlayerTuple> {
    @Override
    public int compare(CardAndPlayerTuple firstCardAndPlayerTuple, CardAndPlayerTuple secondCardAndPlayerTuple) {
        return firstCardAndPlayerTuple.getCard().getCardNumber() - secondCardAndPlayerTuple.getCard().getCardNumber();
    }
}

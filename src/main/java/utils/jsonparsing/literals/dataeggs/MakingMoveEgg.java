package utils.jsonparsing.literals.dataeggs;

public class MakingMoveEgg extends DataEgg {
    private boolean moveRespectsGroundOrder;
    private boolean moveCausesLossOfHealth;
    private int numberOfSmallestCardThatHasCausedLoss; // -1 if no loss has happened

    public MakingMoveEgg(boolean moveRespectsGroundOrder, boolean moveCausesLossOfHealth,
                         int numberOfSmallestCardThatHasCausedLoss) {
        super(DataEggType.MAKING_MOVE_EGG);
        this.moveRespectsGroundOrder = moveRespectsGroundOrder;
        this.moveCausesLossOfHealth = moveCausesLossOfHealth;
        this.numberOfSmallestCardThatHasCausedLoss = numberOfSmallestCardThatHasCausedLoss;
    }

    public boolean moveWasValid() {
        return moveRespectsGroundOrder;
    }

    public boolean moveCausesLossOfHealth() {
        return moveCausesLossOfHealth;
    }

    public int getNumberOfSmallestCardThatHasCausedLoss() {
        return numberOfSmallestCardThatHasCausedLoss;
    }
}

package api.dataeggs;

public class MakingMoveEgg {
    private boolean moveRespectsGroundOrder;
    private boolean moveCausesLossOfHealth;
    private int numberOfSmallestCardThatHasCausedLoss; // -1 if no loss has happened

    public MakingMoveEgg(boolean moveRespectsGroundOrder, boolean moveCausesLossOfHealth,
                         int numberOfSmallestCardThatHasCausedLoss) {
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

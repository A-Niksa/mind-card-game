package api.dataeggs;

public class MakingMoveDataEgg {
    private boolean moveRespectsGroundOrder;
    private boolean moveCausesLossOfHealth;
    private int numberOfSmallestCardThatHasCausedLoss; // -1 if no loss has happened

    public MakingMoveDataEgg(boolean moveRespectsGroundOrder, boolean moveCausesLossOfHealth,
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

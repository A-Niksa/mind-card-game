package backend.logic.models.players.bots;

import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Player;

public abstract class Bot extends Player implements Runnable {
    protected final double AVERAGING_DIVISION_CONSTANT;
    protected int joinedGameId;

    protected Bot(int joinedGameId, double averagingDivisionConstant) {
        super(true);
        this.joinedGameId = joinedGameId;
        AVERAGING_DIVISION_CONSTANT = averagingDivisionConstant;
    }

    @Override
    public void run() {
        while (GameManager.gameHasHealthCardsLeft(joinedGameId) &&
                GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
            System.out.println("out");
            if (hand.hasAnyCards()) {
                System.out.println("in");

                sleepFor(5000);
                if (GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
                    continue;
                }

                GameManager.dropCardInGame(joinedGameId, playerId, getSmallestCardFromHand());
            } else {
                sleepFor(100);
            }
        }
    }

    private NumberedCard getSmallestCardFromHand() {
        return hand.getSmallestCard();
    }

    private int getTimeToSleep() {
        long latestActionTimeDifference = GameManager.getLatestActionTimeDifferenceOfGame(joinedGameId);
        double calculatedSleepTime = (latestActionTimeDifference +
                (getNumberOfCards() * 1.0 / AVERAGING_DIVISION_CONSTANT)) / 2;

        return (int) calculatedSleepTime;
    }

    private void sleepFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfCards() {
        return hand.getNumberedCardsList().size();
    }
}

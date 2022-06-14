package backend.logic.models.players.bots;

import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Player;

public abstract class Bot extends Player implements Runnable {
    protected final double AVERAGING_DIVISION_CONSTANT;
    protected final int ADDITIONAL_WAITING_CONSTANT;
    protected int joinedGameId;

    protected Bot(int joinedGameId, double averagingDivisionConstant, int additionalWaitingConstant) {
        super(true);
        this.joinedGameId = joinedGameId;
        AVERAGING_DIVISION_CONSTANT = averagingDivisionConstant;
        ADDITIONAL_WAITING_CONSTANT = additionalWaitingConstant;
    }

    @Override
    public void run() {


        while (GameManager.gameHasHealthCardsLeft(joinedGameId)) {
            if(GameManager.thereHasBeenANinjaRequest(joinedGameId)){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            } else if (hand.hasAnyCards()) {
                try {
                    Thread.sleep(getTimeToSleep());
                } catch (InterruptedException e) {
                    break;
                }

                if (GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
                    while (!GameManager.thereHasBeenANinjaRequest(joinedGameId)){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }

                GameManager.dropCardInGame(joinedGameId, playerId, getSmallestCardFromHand());
            }
            else {
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
                (getNumberOfCards() * 1.0 / AVERAGING_DIVISION_CONSTANT)) / 2 +
                ADDITIONAL_WAITING_CONSTANT;

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

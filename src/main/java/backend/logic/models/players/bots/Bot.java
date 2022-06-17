package backend.logic.models.players.bots;

import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Player;

public abstract class Bot extends Player implements Runnable {
    protected final double AVERAGING_DIVISION_CONSTANT;
    protected final int ADDITIONAL_WAITING_CONSTANT; // in seconds
    protected int joinedGameId;

    protected Bot(int joinedGameId, double averagingDivisionConstant, int additionalWaitingConstant) {
        super(true);
        this.joinedGameId = joinedGameId;
        AVERAGING_DIVISION_CONSTANT = averagingDivisionConstant;
        ADDITIONAL_WAITING_CONSTANT = additionalWaitingConstant;
    }

    @Override
    public void run() {

        outer:
        while (GameManager.gameHasHealthCardsLeft(joinedGameId)) {
            if(GameManager.thereHasBeenANinjaRequest(joinedGameId)){
                System.out.println("there has been a ninja request");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
            else if (hand.hasAnyCards()) {
                System.out.println("have any card");
                System.out.println("want to sleep");
                try {
                    Thread.sleep(getTimeToSleep());
                    System.out.println("awake from sleep");
                } catch (InterruptedException e) {
                    break;
                }


                if (GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
                    System.out.println("before middle while for ninja request");


                    while (GameManager.thereHasBeenANinjaRequest(joinedGameId)){
                        System.out.println("in middle while for ninja request");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            break outer;
                        }
                    }

                    System.out.println("after middle while for ninja request");

                }

                System.out.println("want to drop card");
                GameManager.dropCardInGame(joinedGameId, playerId, getSmallestCardFromHand());
            }

            else {
                System.out.println("don't have any card");


                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    private NumberedCard getSmallestCardFromHand() {
        return hand.getSmallestCard();
    }

    private int getTimeToSleep() {
        long latestActionTimeDifference = GameManager.getLatestActionTimeDifferenceOfGame(joinedGameId);
        double calculatedSleepTime = (latestActionTimeDifference +
                (getNumberOfCards() * 1000.0 / AVERAGING_DIVISION_CONSTANT)) / 2 +
                ADDITIONAL_WAITING_CONSTANT * 1000.0;

        System.out.println((int) calculatedSleepTime);
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

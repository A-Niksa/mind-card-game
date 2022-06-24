package backend.logic.models.players.bots;

import backend.logic.games.GameManager;
import backend.logic.games.components.JudgeUtils;
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
            if(GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
            else if (hand.hasAnyCards()) {
                if (botIsTheOnlyPlayerWithCards()) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        break;
                    }

                    GameManager.dropCardInGame(joinedGameId, playerId, getSmallestCardFromHand());
                } else {
                    try {
                        Thread.sleep(getTimeToSleep());
                    } catch (InterruptedException e) {
                        break;
                    }

                    if (GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
                        while (GameManager.thereHasBeenANinjaRequest(joinedGameId)) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                break outer;
                            }
                        }
                    }

                    GameManager.dropCardInGame(joinedGameId, playerId, getSmallestCardFromHand());
                }
            }

            else {
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

        return (int) calculatedSleepTime;
    }

    public int getNumberOfCards() {
        return hand.getNumberedCardsList().size();
    }

    public boolean botIsTheOnlyPlayerWithCards() {
        int idOfOnlyPlayerWithCards = JudgeUtils.getIdOfOnlyPlayerWithCards(joinedGameId);
        return playerId == idOfOnlyPlayerWithCards;
    }
}

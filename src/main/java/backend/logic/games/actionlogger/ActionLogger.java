package backend.logic.games.actionlogger;

import backend.logic.games.GameManager;
import backend.logic.models.cards.NumberedCard;

import java.util.ArrayDeque;
import java.util.Deque;

public class ActionLogger {
    private final int INITIAL_TIME_DIFFERENCE = 5000; // in ms

    private int gameId;
    private Deque<Action> actionsStack;
    private long penultimateTimeStamp;
    private long latestTimeStamp;
    private long latestTimeDifference;
    private boolean latestActionHasCausedLoss;
    private int smallestCardNumberThatCausedLoss;

    public ActionLogger(int gameId) {
        this.gameId = gameId;

        actionsStack = new ArrayDeque<>();
        penultimateTimeStamp = 0; // initial value
        latestTimeStamp = 0; // initial value
        latestTimeDifference = INITIAL_TIME_DIFFERENCE; // initial value
        latestActionHasCausedLoss = false;
    }

    public void addAction(Action action) {
        actionsStack.push(action);

        updateTimeKeepers();
        makeBotsKnowAboutNewAction();
    }

    public void logLossOfHealthCard(NumberedCard droppedCard) {
        latestActionHasCausedLoss = true;
        smallestCardNumberThatCausedLoss = droppedCard.getCardNumber();
    }

    private void makeBotsKnowAboutNewAction() {
        GameManager.restartGameThreads(gameId);
    }

    private void updateTimeKeepers() {
        if (actionsStack.size() == 1) {
            latestTimeStamp = actionsStack.peek().getTimeStamp();
            return;
        }

        latestTimeDifference = actionsStack.peek().getTimeStamp() - latestTimeStamp;

        penultimateTimeStamp = latestTimeStamp;
        latestTimeStamp = actionsStack.peek().getTimeStamp();
    }

    public void clear() {
        actionsStack.clear();

        penultimateTimeStamp = 0; // initial value
        latestTimeStamp = 0; // initial value
        latestTimeDifference = INITIAL_TIME_DIFFERENCE; // initial value
    }

    public long getLatestTimeDifference() {
        return latestTimeDifference;
    }

    public boolean latestActionHasCausedLoss() {
        return latestActionHasCausedLoss;
    }

    public void setLatestActionHasCausedLoss(boolean latestActionHasCausedLoss) {
        this.latestActionHasCausedLoss = latestActionHasCausedLoss;
    }

    public int getSmallestCardNumberThatCausedLoss() {
        return smallestCardNumberThatCausedLoss;
    }
}

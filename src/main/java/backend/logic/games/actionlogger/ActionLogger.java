package backend.logic.games.actionlogger;

import api.utils.MakingMoveUtils;
import backend.logic.games.Game;
import backend.logic.games.GameManager;

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

    public void logLossOfHealthCard() {
        latestActionHasCausedLoss = true;

        Game game = GameManager.getGameById(gameId);
        smallestCardNumberThatCausedLoss = MakingMoveUtils.getSmallestCardInPlayersHands(game);
    }

    private void makeBotsKnowAboutNewAction() {
        GameManager.restartGameThreads(gameId);
    }

    private void updateTimeKeepers() {
        if (actionsStack.size() == 1) {
            latestTimeStamp = actionsStack.peek().getTimeStamp();
            return;
        }

        if (actionsStack.isEmpty()) {
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

    public int getPlayerIdOfLatestAction() {
        Action latestAction = actionsStack.peek();
        if (latestAction == null) {
            return -1;
        }

        return latestAction.getPlayerId();
    }
}

package backend.logic.games.actionlogger;

import java.util.ArrayDeque;
import java.util.Deque;

public class ActionLogger {
    private final int INITIAL_TIME_DIFFERENCE = 5000; // in ms

    private Deque<Action> actionsStack;
    private long penultimateTimeStamp;
    private long latestTimeStamp;
    private long latestTimeDifference;

    public ActionLogger() {
        actionsStack = new ArrayDeque<>();
        penultimateTimeStamp = 0; // initial value
        latestTimeStamp = 0; // initial value
        latestTimeDifference = INITIAL_TIME_DIFFERENCE; // initial value
    }

    public void addAction(Action action) {
        actionsStack.push(action);

        updateTimeKeepers();
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
}

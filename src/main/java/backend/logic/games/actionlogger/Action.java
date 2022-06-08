package backend.logic.games.actionlogger;

public class Action {
    private long timeStamp;

    public Action() {
        timeStamp = System.currentTimeMillis();
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}

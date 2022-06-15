package backend.logic.games.actionlogger;

public class Action {
    private long timeStamp;
    private int playerId;

    public Action(int playerId) {
        this.playerId = playerId;
        timeStamp = System.currentTimeMillis();
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getPlayerId() {
        return playerId;
    }
}

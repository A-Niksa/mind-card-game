package api.dataeggs.joinablegames;

public class JoinableGame implements Comparable<JoinableGame> {
    private int gameId;
    private int numberOfFreePlayers;
    private int numberOfBots;

    public JoinableGame(int gameId, int numberOfFreePlayers, int numberOfBots) {
        this.gameId = gameId;
        this.numberOfFreePlayers = numberOfFreePlayers;
        this.numberOfBots = numberOfBots;
    }

    public int getGameId() {
        return gameId;
    }

    public int getNumberOfFreePlayers() {
        return numberOfFreePlayers;
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }

    @Override
    public int compareTo(JoinableGame joinableGame) {
        return gameId - joinableGame.getGameId();
    }
}

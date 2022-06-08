package backend.logic.models.players.bots;

import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.models.players.Player;

public abstract class Bot extends Player implements Runnable {
    protected final double AVERAGING_DIVISION_CONSTANT;
    protected int joinedGameId;

    protected Bot(Hand hand, int joinedGameId, double averagingDivisionConstant) {
        super(hand);
        this.joinedGameId = joinedGameId;
        AVERAGING_DIVISION_CONSTANT = averagingDivisionConstant;
    }

    @Override
    public void run() {
        while (GameManager.gameHasHealthCardsLeft(joinedGameId)) {
            // TODO
        }
    }
}

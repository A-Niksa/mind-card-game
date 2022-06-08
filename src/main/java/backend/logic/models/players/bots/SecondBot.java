package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;

public class SecondBot extends Bot {
    protected SecondBot(Hand hand, int joinedGameId) {
        super(hand, joinedGameId, 20);
    }
}

package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;

public class FirstBot extends Bot {
    protected FirstBot(Hand hand, int joinedGameId) {
        super(hand, joinedGameId, 15);
    }
}

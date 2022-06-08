package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;

public class ThirdBot extends Bot {
    protected ThirdBot(Hand hand, int joinedGameId) {
        super(hand, joinedGameId, 25);
    }
}

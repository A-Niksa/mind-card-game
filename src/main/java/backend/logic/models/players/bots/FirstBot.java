package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;

public class FirstBot extends Bot {
    protected FirstBot(int joinedGameId) {
        super(joinedGameId, 15);
    }
}

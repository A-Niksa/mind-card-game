package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;

public class SecondBot extends Bot {
    protected SecondBot(int joinedGameId) {
        super(joinedGameId, 10, 15);
    }
}

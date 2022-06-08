package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;

public class FirstBot extends Bot {
    protected FirstBot(Hand hand) {
        super(hand, 15);
    }
}

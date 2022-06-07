package backend.logic.models.players.bots;

import backend.logic.games.components.Hand;
import backend.logic.models.players.Player;

public abstract class Bot extends Player {
    protected Bot(Hand hand) {
        super(hand);
    }
}

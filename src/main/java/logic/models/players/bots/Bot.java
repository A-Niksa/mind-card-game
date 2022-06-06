package logic.models.players.bots;

import logic.games.components.Hand;
import logic.models.players.Player;

public abstract class Bot extends Player {
    protected Bot(Hand hand) {
        super(hand);
    }
}

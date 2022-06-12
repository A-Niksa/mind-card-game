package backend.logic.models.players;

import backend.logic.games.components.Hand;

public class Human extends Player {
    public Human(Hand hand) {
        super(hand, false);
    }
}

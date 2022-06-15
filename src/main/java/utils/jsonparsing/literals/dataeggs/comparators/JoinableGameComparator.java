package utils.jsonparsing.literals.dataeggs.comparators;

import utils.jsonparsing.literals.dataeggs.joinablegames.JoinableGame;

import java.util.Comparator;

public class JoinableGameComparator implements Comparator<JoinableGame> {
    @Override
    public int compare(JoinableGame firstJoinableGame, JoinableGame secondJoinableGame) {
        return firstJoinableGame.getGameId() - secondJoinableGame.getGameId();
    }
}

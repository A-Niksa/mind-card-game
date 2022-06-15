package utils.jsonparsing.literals.dataeggs.comparators;

import utils.jsonparsing.literals.dataeggs.gamestate.EmojiEgg;

import java.util.Comparator;

public class EmojiEggComparator implements Comparator<EmojiEgg> {
    @Override
    public int compare(EmojiEgg firstEmojiEgg, EmojiEgg secondEmojiEgg) {
        return firstEmojiEgg.getPlayerId() - secondEmojiEgg.getPlayerId();
    }
}

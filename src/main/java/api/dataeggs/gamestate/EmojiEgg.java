package api.dataeggs.gamestate;

import api.dataeggs.DataEgg;
import api.dataeggs.DataEggType;

public class EmojiEgg extends DataEgg implements Comparable<EmojiEgg> {
    private int playerId;
    private Emoji emoji;

    public EmojiEgg(int playerId, Emoji emoji) {
        super(DataEggType.EMOJI_EGG);
        this.playerId = playerId;
        this.emoji = emoji;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    @Override
    public int compareTo(EmojiEgg emojiEgg) {
        return playerId - emojiEgg.getPlayerId();
    }
}

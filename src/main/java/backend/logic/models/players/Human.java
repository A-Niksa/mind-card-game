package backend.logic.models.players;

import api.dataeggs.gamestate.Emoji;

public class Human extends Player {
    private Emoji selectedEmoji;

    public Human() {
        super(false);
        selectedEmoji = Emoji.NOTHING;
    }

    public Emoji getSelectedEmoji() {
        return selectedEmoji;
    }

    public void setSelectedEmoji(Emoji selectedEmoji) {
        this.selectedEmoji = selectedEmoji;
    }
}

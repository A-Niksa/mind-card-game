package backend.logic.games;

import backend.logic.models.players.Human;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private List<Human> humansInLobbyList;

    public Lobby() {
        humansInLobbyList = new ArrayList<>();
    }

    public void addHuman(Human human) {
        humansInLobbyList.add(human);
    }

    public Human removeHumanById(int playerId) {
        for (Human human : humansInLobbyList) {
            if (playerId == human.getPlayerId()) {
                return human;
            }
        }

        return null;
    }
}

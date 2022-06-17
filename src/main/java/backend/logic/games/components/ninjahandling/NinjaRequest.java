package backend.logic.games.components.ninjahandling;

import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

import java.util.HashMap;
import java.util.List;

import static api.dataeggs.ninjarequest.NinjaRequestStatus.*;

public class NinjaRequest {
    private int numberOfHumansInGame;
    private HashMap<Human, NinjaRequestStatus> humanVotesMap;

    public NinjaRequest(Human human, int numberOfHumansInGame, List<Player> playersList) {
        humanVotesMap = new HashMap<>();
        humanVotesMap.put(human, ACCEPTED);
        putOtherHumansToMap(playersList);

        this.numberOfHumansInGame = numberOfHumansInGame;
    }

    private void putOtherHumansToMap(List<Player> playersList) {
        for (Player player : playersList) {
            if (!player.isBot()) {
                humanVotesMap.put((Human) player, WAITING);
            }
        }
    }

    public void addVote(Human human, boolean agreesToNinjaRequest) {
        if (agreesToNinjaRequest) {
            humanVotesMap.put(human, ACCEPTED);
        } else {
            humanVotesMap.put(human, REJECTED);
        }
    }

    public boolean allHumanVotesHaveBeenCast() {
        return humanVotesMap.size() == numberOfHumansInGame;
    }

    public boolean allHumansHaveAgreedOnNinjaRequest() {
        if (!allHumanVotesHaveBeenCast()) {
            return false;
        }

        for (NinjaRequestStatus requestStatus : humanVotesMap.values()) {
            if (requestStatus != ACCEPTED) {
                return false;
            }
        }

        return true;
    }

    public HashMap<Human, NinjaRequestStatus> getHumanVotesMap() {
        return humanVotesMap;
    }
}

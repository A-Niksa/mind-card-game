package backend.logic.games.components.ninjahandling;

import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.models.players.Human;

import java.util.HashMap;

import static api.dataeggs.ninjarequest.NinjaRequestStatus.*;

public class NinjaRequest {
    private int numberOfHumansInGame;
    private HashMap<Human, NinjaRequestStatus> humanVotesMap;

    public NinjaRequest(Human human, int numberOfHumansInGame) {
        humanVotesMap = new HashMap<>();
        humanVotesMap.put(human, ACCEPTED);

        this.numberOfHumansInGame = numberOfHumansInGame;
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

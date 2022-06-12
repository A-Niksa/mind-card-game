package backend.logic.games.components.ninjahandling;

import backend.logic.models.players.Human;

import java.util.HashMap;

public class NinjaRequest {
    private int numberOfHumansInGame;
    private HashMap<Human, Boolean> humanVotesMap;

    public NinjaRequest(Human human, int numberOfHumansInGame) {
        humanVotesMap = new HashMap<>();
        humanVotesMap.put(human, Boolean.TRUE);

        this.numberOfHumansInGame = numberOfHumansInGame;
    }

    public void addVote(Human human, boolean agreesToNinjaRequest) {
        if (!humanVotesMap.containsKey(human)) {
            humanVotesMap.put(human, agreesToNinjaRequest);
        }
    }

    public boolean allHumanVotesHaveBeenCast() {
        return humanVotesMap.size() == numberOfHumansInGame;
    }

    public boolean allHumansHaveAgreedOnNinjaRequest() {
        for (Boolean humanHasAgreed : humanVotesMap.values()) {
            if (!humanHasAgreed) {
                return false;
            }
        }

        return true;
    }
}

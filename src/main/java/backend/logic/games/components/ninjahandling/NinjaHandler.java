package backend.logic.games.components.ninjahandling;

import api.dataeggs.gamestate.HandEgg;
import api.dataeggs.ninjarequest.DroppedCardEgg;
import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.Deck;
import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.cards.NumberedCardComparator;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

import java.util.*;

public class NinjaHandler {
    private int gameId;
    private int numberOfHumansInGame;
    private List<Player> playersList;
    private Deque<NinjaRequest> ninjaRequestsStack;

    public NinjaHandler(int gameId, int numberOfHumansInGame, List<Player> playersList) {
        this.gameId = gameId;
        this.numberOfHumansInGame = numberOfHumansInGame;
        this.playersList = playersList;
        ninjaRequestsStack = new ArrayDeque<>();
    }

    public ArrayList<CardAndPlayerTuple> carryOutRequestAndReturnDroppedCards() {
        if (ninjaRequestsStack.isEmpty()) {
            return new ArrayList<>();
        }

        NinjaRequest request = ninjaRequestsStack.remove();

        ArrayList<CardAndPlayerTuple> smallestCardsList = getSmallestCardsOfPlayersTuples();
        smallestCardsList.sort(new CardTupleComparator());
        Collections.reverse(smallestCardsList);
        dropCardsOfList(smallestCardsList);

        return smallestCardsList;
    }

    private void dropCardsOfList(ArrayList<CardAndPlayerTuple> cardTuplesList) {
        for (CardAndPlayerTuple cardTuple : cardTuplesList) {
            int playerId = cardTuple.getPlayerId();
            NumberedCard card = cardTuple.getCard();
            GameManager.dropCardInGame(gameId, playerId, card);
        }
    }

    private ArrayList<CardAndPlayerTuple> getSmallestCardsOfPlayersTuples() {
        ArrayList<CardAndPlayerTuple> smallestCardsList = new ArrayList<>();
        for (Player player : playersList) {
            Hand hand = player.getHand();
            int playerId = player.getPlayerId();
            smallestCardsList.add(new CardAndPlayerTuple(hand.getSmallestCard(), playerId));
        }

        smallestCardsList.sort(new CardTupleComparator());
        return smallestCardsList;
    }

    public void addRequest(Human human, boolean agreesWithRequest) {
        if (!thereHasBeenARequest()) {
            ninjaRequestsStack.offer(new NinjaRequest(human, numberOfHumansInGame));
        } else {
            NinjaRequest request = ninjaRequestsStack.peek();
            request.addVote(human, agreesWithRequest);
        }
    }

    public boolean thereHasBeenARequest() {
        return ninjaRequestsStack.size() > 0;
    }

    public NinjaRequestStatus getRequestStatus() {
        NinjaRequest request = ninjaRequestsStack.peek();

        if (request.allHumanVotesHaveBeenCast()) {
            if (request.allHumansHaveAgreedOnNinjaRequest()) {
                return NinjaRequestStatus.ACCEPTED;
            } else {
                return NinjaRequestStatus.REJECTED;
            }
        }

        return NinjaRequestStatus.WAITING;
    }
}
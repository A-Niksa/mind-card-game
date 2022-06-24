package backend.logic.games.components.ninjahandling;

import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.games.Game;
import backend.logic.games.GameManager;
import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import backend.logic.models.players.Human;
import backend.logic.models.players.Player;

import java.util.*;

public class NinjaHandler {
    private int gameId;
    private int numberOfHumansInGame;
    private List<Player> playersList;
    private Deque<NinjaRequest> ninjaRequestsStack;
    private boolean shouldShowSmallestCards;
    List<CardAndPlayerTuple> smallestCardsList;

    public NinjaHandler(int gameId, int numberOfHumansInGame, List<Player> playersList) {
        this.gameId = gameId;
        this.numberOfHumansInGame = numberOfHumansInGame;
        this.playersList = playersList;
        ninjaRequestsStack = new ArrayDeque<>();
//        fillNinjaRequestsStack();
        shouldShowSmallestCards = false;
    }

//    private void fillNinjaRequestsStack() {
//        Game game = GameManager.getGameById(gameId);
//        int numberOfHumans = game.getNumbexrOfPlayers() - game.getNumberOfBots();
//        for (Player player : playersList) {
//            if (!player.isBot()) {
//                Human human = (Human) player;
//                ninjaRequestsStack.offer(new NinjaRequest(human, numberOfHumans));
//            }
//        }
//    }

    public ArrayList<CardAndPlayerTuple> carryOutRequestAndReturnDroppedCards() {

        ArrayList<CardAndPlayerTuple> smallestCardsList = getSmallestCardsOfPlayersTuples();
        if (smallestCardsList.isEmpty()) {
            return smallestCardsList;
        }

        smallestCardsList.sort(new CardTupleComparator());
        Collections.reverse(smallestCardsList);

        CardAndPlayerTuple largestTuple = smallestCardsList.get(0);
        GameManager.dropCardInGame(gameId, largestTuple.getPlayerId(), largestTuple.getCard());

        return smallestCardsList;
    }

//    private void dropCardOnGame(ArrayList<CardAndPlayerTuple> cardTuplesList) {
//        for (CardAndPlayerTuple cardTuple : cardTuplesList) {
//            int playerId = cardTuple.getPlayerId();
//            NumberedCard card = cardTuple.getCard();
//            GameManager.dropCardInGame(gameId, playerId, card);
//        }
//    }

    private ArrayList<CardAndPlayerTuple> getSmallestCardsOfPlayersTuples() {
        ArrayList<CardAndPlayerTuple> smallestCardsList = new ArrayList<>();
        for (Player player : playersList) {
            Hand hand = player.getHand();
            if (!hand.hasAnyCards()){
                continue;
            }

            int playerId = player.getPlayerId();
            smallestCardsList.add(new CardAndPlayerTuple(hand.getSmallestCard(), playerId));
        }

        smallestCardsList.sort(new CardTupleComparator());
        return smallestCardsList;
    }

    public void addRequest(Human human, boolean agreesWithRequest) {
        if (!thereHasBeenARequest()) {
            ninjaRequestsStack.offer(new NinjaRequest(human, numberOfHumansInGame, playersList));
        } else {
            NinjaRequest request = ninjaRequestsStack.peek();
            if (request == null) {
                return;
            }

            request.addVote(human, agreesWithRequest);

//            if (!agreesWithRequest || request.allHumansHaveAgreedOnNinjaRequest()) {
//                ninjaRequestsStack.remove();
//                GameManager.restartGameThreads(gameId);
//            }
            if (!agreesWithRequest) {
                ninjaRequestsStack.remove();
                GameManager.restartGameThreads(gameId);
            }
        }
    }

    public boolean thereHasBeenARequest() {
        return ninjaRequestsStack.size() > 0;
    }

    public NinjaRequestStatus getRequestStatus() {
        NinjaRequest request = ninjaRequestsStack.peek();

        if(request == null){
            return NinjaRequestStatus.WAITING;
        }
        else if (request.allHumanVotesHaveBeenCast()) {
            if (request.allHumansHaveAgreedOnNinjaRequest()) {
                return NinjaRequestStatus.ACCEPTED;
            } else {
                return NinjaRequestStatus.REJECTED;
            }
        }

        return NinjaRequestStatus.WAITING;
    }

    public Deque<NinjaRequest> getNinjaRequestsStack() {
        return ninjaRequestsStack;
    }

    public List<CardAndPlayerTuple> getSmallestCardsList() {
        return smallestCardsList;
    }

    public void setSmallestCardsList(List<CardAndPlayerTuple> smallestCardsList) {
        this.smallestCardsList = smallestCardsList;
    }

    public boolean shouldShowSmallestCards() {
        return shouldShowSmallestCards;
    }

    public void setShouldShowSmallestCards(boolean shouldShowSmallestCards) {
        this.shouldShowSmallestCards = shouldShowSmallestCards;
    }

    public void setNumberOfHumansInGame(int numberOfHumansInGame) {
        this.numberOfHumansInGame = numberOfHumansInGame;
    }
}
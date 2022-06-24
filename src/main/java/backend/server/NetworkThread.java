package backend.server;

import api.API;
import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class NetworkThread implements Runnable{
    private int id;
    private static int idCounter = 0;
    private Socket socket;
    private String authToken;


    public NetworkThread(Socket socket) {
        this.socket = socket;
        id = idCounter;
        idCounter++;
    }

    @Override
    public void run() {
        DataInputStream inputStream = null;
        DataOutputStream outputStream = null;

        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean notBroken = true;

        authToken = AuthTokenGenerator.nextToken();

        try {
            outputStream.writeUTF(authToken);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (notBroken){

            String input = "";

            try {
                if(inputStream.readUTF().equals(authToken)){
                    outputStream.writeBoolean(true);
                }
                else{
                    outputStream.writeBoolean(false);
                    continue;
                }

                input = inputStream.readUTF();
                checkConditionAndDoAction(input, inputStream, outputStream);

            }
            catch (IOException e) {
                e.printStackTrace();
                notBroken = false;
            }

        }
    }

    public void checkConditionAndDoAction(String input, DataInputStream inputStream, DataOutputStream outputStream){

        if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.TEST_CONNECTION))){
            try {
                var b = inputStream.readBoolean();

                System.out.println("Client : " + id + "  send boolean  " + b);

                if(b){
                    outputStream.writeBoolean(true);
                }
                else{
                    outputStream.writeBoolean(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.NO_ACTION))){
            //  No action
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.ADD_NEW_PLAYER_IN_NETWORK))){
            try {
                outputStream.writeInt(API.addNewPlayerToLobby());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.ADD_NEW_GAME))){
            try {
                outputStream.writeUTF(API.addNewGame(inputStream.readInt() , inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.JOIN_GAME))){
            try {
                outputStream.writeBoolean(API.joinGame(inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.ALL_JOINABLE_GAMES))){
            try {
                outputStream.writeUTF(API.getAllJoinableGames());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.UPDATE_GAME))){
            try {
                outputStream.writeUTF(API.getUpdatedGameState(inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.MOVE_A_CARD))){
            try {
                outputStream.writeUTF(API.makeMove(inputStream.readInt(), inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.MAKE_GAME_UNJOINABLE))){
            try {
                outputStream.writeBoolean(API.startGame(inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.SEND_REQUEST))){
            try {
                API.castNinjaVote(inputStream.readBoolean(), inputStream.readInt(), inputStream.readInt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.HAS_GAME_STARTED))){
            try {
                outputStream.writeBoolean(API.gameHasStarted(inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.GET_HOST_ID))){
            try {
                outputStream.writeInt(API.getHostId(inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.SET_EMOJI))){
            try {
                API.setEmoji(inputStream.readInt(), inputStream.readInt(), inputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.CAST_NINJA_CARD))){
            try {
                API.castNinjaVote(inputStream.readBoolean(), inputStream.readInt(), inputStream.readInt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigFetcher.fetch(ConfigIdentifier.SHOWED_SMALLEST_CARDS))){
            try {
                API.showedSmallestCards(inputStream.readInt());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

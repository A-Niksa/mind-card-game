package frontend.client;

import api.dataeggs.gamestate.Emoji;
import utils.config.DefaultConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNetwork {
    private Socket socket;

    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    String authToken;

    public ClientNetwork() {

        try {
            socket = new Socket(DefaultConfig.IP, DefaultConfig.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connected to the server");

        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            authToken = inputStream.readUTF();
            System.out.println(authToken);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


    public boolean testConnection(){
        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
               return false;
            }
            outputStream.writeUTF(DefaultConfig.TestConnection);
            boolean b = true;

            try {
                outputStream.writeBoolean(true);
                b = inputStream.readBoolean();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return b & socket.isConnected();

        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void noAction(){
        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return;
            }

            outputStream.writeUTF(DefaultConfig.NoAction);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int addNewPlayer(){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return -1;
            }

            outputStream.writeUTF(DefaultConfig.AddNewPlayerInNetwork);
            return inputStream.readInt();

        }
        catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public String creatNewGame(int numberOfBots, int playerId){
        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return "";
            }

            outputStream.writeUTF(DefaultConfig.AddNewGame);
            outputStream.writeInt(numberOfBots);
            outputStream.writeInt(playerId);
            return inputStream.readUTF();
        }
        catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean joinGame(int idGame, int playerId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return false;
            }

            outputStream.writeUTF(DefaultConfig.JoinGame);
            outputStream.writeInt(idGame);
            outputStream.writeInt(playerId);
            return inputStream.readBoolean();
        }

        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String allJoinableGames(){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return "";
            }

            outputStream.writeUTF(DefaultConfig.AllJoinableGames);
            return inputStream.readUTF();
        }

        catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public String updateGame(int gameId, int currentHumanId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return "";
            }

            outputStream.writeUTF(DefaultConfig.UpdateGame);
            outputStream.writeInt(gameId);
            outputStream.writeInt(currentHumanId);
            return inputStream.readUTF();
        }

        catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public String makeMove(int gameId, int playerId, int cardNumber){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return "";
            }

            outputStream.writeUTF(DefaultConfig.MakeMoveACard);
            outputStream.writeInt(gameId);
            outputStream.writeInt(playerId);
            outputStream.writeInt(cardNumber);
            return inputStream.readUTF();
        }

        catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public boolean makeGameUnjoinable(int gameId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return false;
            }

            outputStream.writeUTF(DefaultConfig.MakeGameUnjoinable);
            outputStream.writeInt(gameId);
            return inputStream.readBoolean();
        }

        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isGameStarted(int gameId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return false;
            }

            outputStream.writeUTF(DefaultConfig.IsGameStarted);
            outputStream.writeInt(gameId);
            return inputStream.readBoolean();
        }

        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public int getHostId(int gameId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return -1;
            }

            outputStream.writeUTF(DefaultConfig.getHostId);
            outputStream.writeInt(gameId);
            return inputStream.readInt();
        }

        catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public void useNinjaCard(int gameId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return;
            }

            outputStream.writeUTF(DefaultConfig.MakeGameUnjoinable);
            outputStream.writeInt(gameId);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void castNinjaVote(boolean agreesWithRequest, int playerId, int gameId){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return;
            }

            outputStream.writeUTF(DefaultConfig.castNinjaCard);
            outputStream.writeBoolean(agreesWithRequest);
            outputStream.writeInt(playerId);
            outputStream.writeInt(gameId);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setEmoji(int gameId, int playerId, Emoji emoji){

        try {
            outputStream.writeUTF(authToken);
            if(!inputStream.readBoolean()){
                return;
            }

            outputStream.writeUTF(DefaultConfig.setEmoji);
            outputStream.writeInt(gameId);
            outputStream.writeInt(playerId);
            outputStream.writeUTF(emoji.name());

        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }



    


}

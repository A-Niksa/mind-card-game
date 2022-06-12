package frontend.client;

import utils.config.DefaultConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNetwork {
    private Socket socket;

    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;

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


    }


    public boolean testConnection(){
        try {
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
            outputStream.writeUTF(DefaultConfig.NoAction);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int addNewPlayer(){

        try {
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

    public boolean joinGame(int idGame){

        try {
            outputStream.writeUTF(DefaultConfig.JoinGame);
            return inputStream.readBoolean();
        }

        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String allJoinableGames(){

        try {
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
            outputStream.writeUTF(DefaultConfig.UpdateGame);
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

    


}

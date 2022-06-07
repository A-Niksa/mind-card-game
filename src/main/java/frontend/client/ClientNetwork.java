package frontend.gui;

import config.ConfigClass;

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
            socket = new Socket(ConfigClass.IP, ConfigClass.PORT);
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
            outputStream.writeUTF(ConfigClass.TestConnection);
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
            outputStream.writeUTF(ConfigClass.NoAction);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int addNewPlayer(){

        try {
            outputStream.writeUTF(ConfigClass.AddNewPlayerInNetwork);
            return inputStream.readInt();

        }
        catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public String creatNewGame(int numberOfBots, int playerId){
        try {
            outputStream.writeUTF(ConfigClass.AddNewGame);
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
            outputStream.writeUTF(ConfigClass.JoinGame);
            return inputStream.readBoolean();
        }

        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public String allJoinableGames(){

        try {
            outputStream.writeUTF(ConfigClass.AllJoinableGames);
            return inputStream.readUTF();
        }

        catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }

    public String updateGame(int gameId, int currentHumanId){

        try {
            outputStream.writeUTF(ConfigClass.UpdateGame);
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
            outputStream.writeUTF(ConfigClass.UpdateGame);
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

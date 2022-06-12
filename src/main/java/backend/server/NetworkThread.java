package backend.server;

import api.API;
import config.ConfigClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkThread implements Runnable{
    private int id;
    private static int idCounter = 0;
    private Socket socket;

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
        while (notBroken){
            String input = "";

            try {
                input = inputStream.readUTF();
                checkConditionAndDoAction(input, inputStream, outputStream);

            } catch (IOException e) {
                e.printStackTrace();
                notBroken = false;
            }

        }
    }

    public void checkConditionAndDoAction(String input, DataInputStream inputStream, DataOutputStream outputStream){

        if(input.equals(ConfigClass.TestConnection)){
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

        else if(input.equals(ConfigClass.NoAction)){
            //  No action
        }

        else if(input.equals(ConfigClass.AddNewPlayerInNetwork)){
            try {
                outputStream.writeInt(API.addNewPlayer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigClass.AddNewGame)){
            try {
                outputStream.writeUTF(API.addNewGame(inputStream.readInt() , inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigClass.JoinGame)){
            try {
                outputStream.writeBoolean(API.joinGame(inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigClass.AllJoinableGames)){
            try {
                outputStream.writeUTF(API.getAllJoinableGames());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigClass.UpdateGame)){
            try {
                outputStream.writeUTF(API.getUpdatedGameState(inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(ConfigClass.MakeMoveACard)){
            try {
                outputStream.writeUTF(API.makeMove(inputStream.readInt(), inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

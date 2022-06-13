package backend.server;

import api.API;
import utils.config.DefaultConfig;

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
        System.out.println(authToken);
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
                System.out.println(input);
                checkConditionAndDoAction(input, inputStream, outputStream);

            } catch (IOException e) {
                e.printStackTrace();
                notBroken = false;
            }

        }
    }

    public void checkConditionAndDoAction(String input, DataInputStream inputStream, DataOutputStream outputStream){

        if(input.equals(DefaultConfig.TestConnection)){
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

        else if(input.equals(DefaultConfig.NoAction)){
            //  No action
        }

        else if(input.equals(DefaultConfig.AddNewPlayerInNetwork)){
            try {
                outputStream.writeInt(API.addNewPlayerToLobby());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.AddNewGame)){
            try {
                outputStream.writeUTF(API.addNewGame(inputStream.readInt() , inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.JoinGame)){
            try {
                outputStream.writeBoolean(API.joinGame(inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.AllJoinableGames)){
            try {
                outputStream.writeUTF(API.getAllJoinableGames());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.UpdateGame)){
            try {
                outputStream.writeUTF(API.getUpdatedGameState(inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.MakeMoveACard)){
            try {
                outputStream.writeUTF(API.makeMove(inputStream.readInt(), inputStream.readInt(), inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.MakeGameUnjoinable)){
            try {
                outputStream.writeBoolean(API.makeGameUnjoinable(inputStream.readInt()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.SendRequest)){
            try {
                API.sendNinjaRequest(inputStream.readBoolean(), inputStream.readInt(), inputStream.readInt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(input.equals(DefaultConfig.UseNinjaCard)){
            try {
                API.useNinjaCard(inputStream.readInt(), inputStream.readInt());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

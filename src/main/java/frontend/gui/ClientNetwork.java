package frontend.gui;

import config.ConfigClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNetwork {
    private Socket socket;
    private int playerId;

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

    public boolean TestConnection(){
        try {
            outputStream.writeUTF(ConfigClass.AddNewPlayerInNetwork);
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
}

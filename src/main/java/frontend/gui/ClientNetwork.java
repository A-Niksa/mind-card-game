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

    public boolean TestConnection(){
        try {
            outputStream.writeBoolean(true);
            System.out.println(inputStream.readBoolean());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(socket.isConnected()){
            return true;
        }
        else{
            return false;
        }

    }
}

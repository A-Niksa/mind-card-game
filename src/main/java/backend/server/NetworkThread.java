package backend.server;

import api.API;

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

        try {
            outputStream.writeInt(API.addNewPlayer());
        } catch (IOException e) {
            e.printStackTrace();
        }



//        while (true){
//            try {
//                var b = inputStream.readBoolean();
//
//                System.out.println("Client : " + id + "  send boolean  " + b);
//
//                if(b){
//                    outputStream.writeBoolean(true);
//                }
//                else{
//                    outputStream.writeBoolean(false);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}

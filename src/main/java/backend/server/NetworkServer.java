package backend.server;

import utils.config.DefaultConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(DefaultConfig.PORT);
        System.out.println("server started");
        while (true) {
            System.out.println("Waiting for new client: ");
            Socket socket = serverSocket.accept();

            System.out.println("client with ip : " + socket.getRemoteSocketAddress().toString() + " is connected");

            new Thread(new NetworkThread(socket)).start();
        }
    }
}

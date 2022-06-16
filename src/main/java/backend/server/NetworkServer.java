package backend.server;

import utils.config.DefaultConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(DefaultConfig.PORT);

        while (true) {
            Socket socket = serverSocket.accept();

            new Thread(new NetworkThread(socket)).start();
        }
    }
}

package backend.server;

import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(ConfigFetcher.fetch(ConfigIdentifier.PORT));
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();

            new Thread(new NetworkThread(socket)).start();
        }
    }
}

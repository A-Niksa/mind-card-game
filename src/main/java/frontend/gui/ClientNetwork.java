package frontend.gui;

import config.ConfigClass;

import java.io.IOException;
import java.net.Socket;

public class ClientNetwork {
    Socket socket;

    {
        try {
            socket = new Socket(ConfigClass.IP, ConfigClass.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

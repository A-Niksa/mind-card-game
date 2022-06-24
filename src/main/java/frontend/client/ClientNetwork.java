package frontend.client;

import api.dataeggs.gamestate.Emoji;
import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNetwork {
    private Socket socket;

    DataInputStream inputStream = null;
    DataOutputStream outputStream = null;
    String authToken;

    public ClientNetwork() {

        try {
            String ip = ConfigFetcher.fetch(ConfigIdentifier.IP);
            int port = Integer.parseInt(ConfigFetcher.fetch(ConfigIdentifier.PORT));
            socket = new Socket(ip, port);
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

        try {
            authToken = inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public boolean testConnection() {
        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return false;
            }
            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.TEST_CONNECTION));
            boolean b = true;

            try {
                outputStream.writeBoolean(true);
                b = inputStream.readBoolean();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return b & socket.isConnected();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void noAction() {
        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.NO_ACTION));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int addNewPlayer() {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return -1;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.ADD_NEW_PLAYER_IN_NETWORK));
            return inputStream.readInt();

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return -1;
        }

    }

    public String createNewGame(int numberOfBots, int playerId) {
        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return "";
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.ADD_NEW_GAME));
            outputStream.writeInt(numberOfBots);
            outputStream.writeInt(playerId);
            return inputStream.readUTF();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return "";
        }
    }

    public boolean joinGame(int idGame, int playerId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return false;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.JOIN_GAME));
            outputStream.writeInt(idGame);
            outputStream.writeInt(playerId);
            return inputStream.readBoolean();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }

    }

    public String allJoinableGames() {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return "";
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.ALL_JOINABLE_GAMES));
            return inputStream.readUTF();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return "";
        }

    }

    public String updateGame(int gameId, int currentHumanId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return "";
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.UPDATE_GAME));
            outputStream.writeInt(gameId);
            outputStream.writeInt(currentHumanId);
            return inputStream.readUTF();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return "";
        }

    }

    public String makeMove(int gameId, int playerId, int cardNumber) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return "";
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.MOVE_A_CARD));
            outputStream.writeInt(gameId);
            outputStream.writeInt(playerId);
            outputStream.writeInt(cardNumber);
            return inputStream.readUTF();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return "";
        }

    }

    public boolean makeGameUnjoinable(int gameId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return false;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.MAKE_GAME_UNJOINABLE));
            outputStream.writeInt(gameId);
            return inputStream.readBoolean();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }

    }

    public boolean isGameStarted(int gameId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return false;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.HAS_GAME_STARTED));
            outputStream.writeInt(gameId);
            return inputStream.readBoolean();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }

    }

    public int getHostId(int gameId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return -1;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.GET_HOST_ID));
            outputStream.writeInt(gameId);
            return inputStream.readInt();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return -1;
        }

    }

    public void castNinjaVote(boolean agreesWithRequest, int playerId, int gameId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.CAST_NINJA_CARD));
            outputStream.writeBoolean(agreesWithRequest);
            outputStream.writeInt(playerId);
            outputStream.writeInt(gameId);
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    public void setEmoji(int gameId, int playerId, Emoji emoji) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.SET_EMOJI));
            outputStream.writeInt(gameId);
            outputStream.writeInt(playerId);
            outputStream.writeUTF(emoji.name());

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    public void showedSmallestCards(int gameId) {

        try {
            outputStream.writeUTF(authToken);
            if (!inputStream.readBoolean()) {
                return;
            }

            outputStream.writeUTF(ConfigFetcher.fetch(ConfigIdentifier.SHOWED_SMALLEST_CARDS));
            outputStream.writeInt(gameId);

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

}

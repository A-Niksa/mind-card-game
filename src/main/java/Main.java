import frontend.client.ClientNetwork;
import frontend.gui.firstMenuPage.FirstMenuPage;

public class Main {
    public static void main(String[] args) {


        var clientNetwork = new ClientNetwork();
        int playerId = clientNetwork.addNewPlayer();
        new FirstMenuPage(clientNetwork, playerId);


        while (true){
            System.out.println("id: " + playerId);
            System.out.println("Test connection: " + clientNetwork.testConnection());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

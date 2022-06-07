import frontend.gui.ClientNetwork;

public class Main {
    public static void main(String[] args) {
        var clientNetwork = new ClientNetwork();


        while (true){
            clientNetwork.TestConnection();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

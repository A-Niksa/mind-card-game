import frontend.gui.ClientNetwork;
import frontend.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
//        new MainFrame();

        var clientNetwork = new ClientNetwork();
        var v = clientNetwork.addNewPlayer();

        while (true){
            System.out.println("id : " + v);
            System.out.println("Test connection : " + clientNetwork.testConnection());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}

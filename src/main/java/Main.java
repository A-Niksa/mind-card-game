import frontend.gui.ClientNetwork;

public class Main {
    public static void main(String[] args) {
        var clientNetwork = new ClientNetwork();
        var v = clientNetwork.addNewPlayer();



        while (true){
            System.out.println("id : " + v);
            System.out.println("Test connection : " + clientNetwork.TestConnection());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
}

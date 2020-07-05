/**
 * Created by ArtemZ on 20.05.17.
 */
import javax.swing.JFrame;
import java.io.IOException;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        Client client;
        client = new Client("192.168.0.180");
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.startRunning();

        //ClientFile.ClientFileOperation("192.168.0.180");


    }

}

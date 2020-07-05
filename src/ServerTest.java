/**
 * Created by ArtemZ on 19.05.17.
 */
import javax.swing.JFrame;
import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.startRunning();

        //ServerFiles.ServerFilesOperation();
    }
}

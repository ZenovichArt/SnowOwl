import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFiles {

    public static ServerSocket serverSocket;
    public static Socket socket;
    public InputStream in;
    public OutputStream out;
    public static int fileLength;

        public static void ServerFilesOperation() throws IOException {
            //ServerSocket serverSocket = null;

            serverSocket = new ServerSocket(6942);

            /*try {
                serverSocket = new ServerSocket(63342);
            } catch (IOException ex) {
                System.out.println("Can't setup server on this port number. ");
            }*/

            System.out.println("\n\nServer is ready to receive a file");

            socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            //OutputStream out = socket.getOutputStream();


            /*Socket socket = null;
            InputStream in = null;
            OutputStream out = null;*/

            /*try {
                socket = serverSocket.accept();
            } catch (IOException ex) {
                System.out.println("Can't accept client connection. ");
            }*/

            /*try {
                in = socket.getInputStream();
            } catch (IOException ex) {
                System.out.println("Can't get socket input stream. ");
            }

            try {
                out = new FileOutputStream("/Users/MacBook/Desktop/test.log");
            } catch (FileNotFoundException ex) {
                System.out.println("File not found. ");
            }*/

            fileLength = in.read() + 10000;

            //byte[] bytes = new byte[16*1024];
            byte[] bytes = new byte[fileLength];

            FileOutputStream fout = new FileOutputStream("/Users/artemzenovich/Desktop/test.log");
            BufferedOutputStream bout = new BufferedOutputStream(fout);
            int recievedBytes = in.read(bytes, 0, bytes.length);
            int current = recievedBytes;

            do {
                recievedBytes = in.read(bytes, current, bytes.length - current);
                if (recievedBytes >= 0)
                    current += recievedBytes;
            } while (recievedBytes > -1);

            bout.write(bytes, 0, current);
            bout.flush();
            System.out.println("File /Users/artemzenovich/Desktop/test.log downloaded (" + current + " bytes downloaded)");


            /*int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
             */


            bout.close();
            in.close();
            socket.close();
            serverSocket.close();
        }
}

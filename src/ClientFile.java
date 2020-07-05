import java.io.*;
import java.net.Socket;

public class ClientFile {

    public static void ClientFileOperation(String host) throws IOException {

        Socket socket = new Socket(host, 6942);

        File file = new File("/Users/MacBook/Desktop/test.log");
        // Get the size of the file

        if (file.isFile()) {

            byte[] fileInBytes = new byte[(int)file.length()];

            InputStream in = new FileInputStream(file);
            BufferedInputStream bin = new BufferedInputStream(in);
            bin.read(fileInBytes, 0, fileInBytes.length);

            OutputStream out = socket.getOutputStream();
            out.write((int)file.length());
            System.out.println("Sending file (" + fileInBytes.length + ")");
            out.write(fileInBytes, 0, fileInBytes.length);
            out.flush();
            System.out.println("File was sent");

            in.close();
            out.close();

            /*int count;
            while ((count = in.read(fileInBytes)) > 0) {
                out.write(fileInBytes, 0, count);
            }

            out.close();
            in.close();
            System.out.println("File was sent");*/


        }
            socket.close();
    }
}
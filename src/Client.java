/**
 * Created by ArtemZ on 28.06.20.
 */

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

public class Client extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    public Socket connection;

    public Client (String host) throws IOException {
        super("Snow Owl");
        serverIP = host;
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            sendMessage(event.getActionCommand());
                            userText.setText("");
                        }
                }
        );
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(300, 500);
        setVisible(true);
    }

    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            whileChatting();
        } catch (EOFException eofException) {
            showMessage("\nClient terminated connection");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            shutdown();
        }
    }

    private void connectToServer() throws IOException {
        showMessage("Attempting connection...\n");
        connection = new Socket(InetAddress.getByName(serverIP), 63342);
        //connection = new Socket("192.168.0.180", 63342);
        showMessage("Connected to: " + connection.getInetAddress().getHostName() + "\n");
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("Streams connected");
        showMessage("\nConnection succeed \n");
    }

    private void whileChatting() throws IOException {
        ableToType(true);
        historyDownload();
        do {
            try {
                message = (String) input.readObject();
                checkSendFile(message);
                showMessage("\nServer - " + message);
                historyUpload("\nServer - " + message);
            } catch (ClassNotFoundException classNotFoundException) {
                showMessage("\nUnknown object type");
            }

        } while (!message.equals(".bye"));
    }

    public void shutdown() {
        showMessage("Closing everything");
        ableToType(false);
        try {
            output.close();
            input.close();
            connection.close();
            dispose();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            output.writeObject(message);
            output.flush();
            checkSendFile(message);
            historyUpload("\nClient - " + message);
            showMessage("\nClient - " + message);
        } catch (IOException ioException) {
            chatWindow.append("\n SENDING ERROR");
        }
    }

    public void showMessage(final String m) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatWindow.append(m);
                    }
                }
        );
    }

    private void ableToType(final boolean tof) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        userText.setEditable(tof);
                    }
                }
        );
    }

    File historyFile = new File("/Users/MacBook/IdeaProjects/SnowOwl/history.log");
    Scanner scanner = new Scanner(historyFile);
    FileWriter fileWriter = new FileWriter(historyFile, true);

    private void historyUpload (String message) throws IOException {
        if (message.charAt(0) == '\n')
            fileWriter.write(message.substring(1) + "\n");
        else
            fileWriter.write(message + "\n");
        fileWriter.flush();
    }

    private void historyDownload () {
        showMessage("\nHistory of previous messages:\n");
        while (scanner.hasNextLine()) {
            showMessage(scanner.nextLine() + "\n");
        }
        showMessage("\n");
    }

    private void checkSendFile (String message) throws IOException {
        if (message.equals(".send file"))
            ClientFile.ClientFileOperation(serverIP);
    }


}

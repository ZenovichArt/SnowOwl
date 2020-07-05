/**
 * Created by ArtemZ on 28.06.20.
 */

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

public class Server extends JFrame {

    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;


    public Server() throws IOException {
        super("Snow Owl");
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
        add(new JScrollPane(chatWindow));
        setSize(300, 500);
        setVisible(true);
    }

    public void startRunning() {
        try {
            server = new ServerSocket(63342, 5);
            while(true) {
                try {
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                }catch(EOFException eofException) {
                    showMessage("\n Server ended the connection ");
                } finally{
                    closeConnection();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        showMessage(" Waiting for connection \n");
        connection = server.accept();
        showMessage(" Now connected to " + connection.getInetAddress().getHostName());
    }


    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        showMessage("\n Streams are now setup \n");
    }

    //during the chat conversation
    private void whileChatting() throws IOException {
        String message = "";
        historyDownload();
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                checkSendFile(message);
                showMessage("\nClient - " + message);
                historyUpload("Client - " + message);
            }catch(ClassNotFoundException classNotFoundException) {
                showMessage("The user has sent an unknown object");
            }
        }while(!message.equals(".bye"));
    }

    public void closeConnection() {
        showMessage("\n Closing Connections... \n");
        ableToType(false);
        try{
            output.close(); //Closes the output path to the client
            input.close(); //Closes the input path to the server, from the client.
            connection.close();
            dispose();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }


    private void sendMessage(String message){
        try{
            output.writeObject(message);
            output.flush();

            checkSendFile(message);

            historyUpload("\nServer - " + message);
            showMessage("\nServer - " + message);
        }catch(IOException ioException){
            chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }
    }


    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        chatWindow.append(text);
                    }
                }
        );
    }

    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        userText.setEditable(tof);
                    }
                }
        );
    }

    File historyFile = new File("/Users/artemzenovich/Downloads/chatRoom/history.log");
    Scanner scanner = new Scanner(historyFile);
    FileWriter fileWriter = new FileWriter(historyFile, true);

    private void historyUpload (String message) throws IOException {
        if (message.charAt(0) == '\n')
            fileWriter.write(message.substring(1) + "\n");
        else
            fileWriter.write(message + "\n");
        fileWriter.flush();
    }

    private void historyDownload() {
        showMessage("\n History of previous messages:\n");
        while (scanner.hasNextLine())
            showMessage(scanner.nextLine() + "\n");
        showMessage("\n");
    }

    private void checkSendFile(String message) throws IOException {
        if (message.equals(".send file"))
            ServerFiles.ServerFilesOperation();
    }




}
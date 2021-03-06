import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    private static String name;
    //public String name;
    private JTextField username;
    private JButton serverButton;
    private JButton clientButton;
    private Server server;
    private Client client;

    public MainMenu() {

        super("Snow Owl");
        setSize(300, 100);
        username = new JTextField();
        username.setEditable(true);
        add(username, BorderLayout.NORTH);
        username.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        name = event.getActionCommand();
                        username.setEditable(false);
                    }
                }
        );

        serverButton = new JButton("Become a host");
        add(serverButton, BorderLayout.CENTER);
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.super.dispose();
                server = new Server();
                server.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                server.startRunning();

            }
        });

        clientButton = new JButton("Connect to host");
        add(clientButton, BorderLayout.SOUTH);
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainMenu.super.dispose();
                dispose();
                client = new Client("192.168.0.180");
                client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                client.startRunning();
            }
        });

    }

    public static String getNickname() {
        return name;
    }
}

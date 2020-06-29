import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public String name;
    private JTextField username;
    private JButton serverButton;
    private JButton clientButton;
    private Server server;
    private Client client;

    public MainMenu() {

        super("Snow Owl");
        username = new JTextField();
        username.setEditable(true);
        setSize(300, 100);
        add(username, BorderLayout.NORTH);
        username.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        name = event.getActionCommand();
                        System.out.println(name);
                        username.setEditable(false);
                    }
                }
        );

        serverButton = new JButton("Become a host");
        add(serverButton, BorderLayout.CENTER);
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server = new Server();
                MainMenu.super.dispose();
                server.startRunning();
            }
        });

        clientButton = new JButton("Connect to host");
        add(clientButton, BorderLayout.SOUTH);
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = new Client("127.0.0.1");
                MainMenu.super.dispose();
                client.startRunning();
            }
        });

    }
}

package GUI;

import ClientChat.ClientMain;
import ClientChat.ThreadInvio;
import ClientChat.ThreadRicevi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.awt.Font.*;


public class ChatGui extends JFrame {

    private String title;
    private int width, height;
    private JTextField usernameField;
    private Socket socket;
    private JTextArea textArea;
    private int flag = 0;

    public ChatGui(int width, int height, String title) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.title = title;
        this.width = width;
        this.height = height;

        try {
            socket = new Socket("localhost",5500);
        } catch (IOException e) {
            System.out.println("Impossibile connettersi al server");
        }

    }

    public void display(boolean mode) {
        setVisible(mode);
    }

    public void logIn() {
        setSize(width, height);
        GridLayout gridLayout = new GridLayout(2, 1);
        setLayout(gridLayout);


        JLabel label = new JLabel("LogIn", SwingConstants.CENTER);
        label.setFont(new Font("Arial", BOLD, 20));
        add(label);

        //FlowLayout flowLayout = new FlowLayout();
        JPanel inputPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(15);
        JButton submitButton = new JButton("OK");
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(submitButton);

        submitButton.addActionListener(e -> {
            Socket clientSocket;
            try {
                Thread invioThread = new Thread(new ThreadInvio(socket, usernameField.getText()));
                Thread riceviThread = new Thread(new ThreadRicevi(socket, this));
                invioThread.start();
                try {
                    invioThread.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {

                    showChat();
                }
                riceviThread.start();
            } catch (IOException i) {
                System.out.println("Impossibile connettersi con il server");
            }


        });


        add(inputPanel);
    }

    public int getFlag() {
        return flag;
    }


    public void appendMsg(String msg) {
        textArea.append(msg + "\n");
    }



    public void showChat() {
        //setVisible(true);

        setSize(width, height);
        GridLayout gridLayout = new GridLayout(3, 1);
        setLayout(gridLayout);

        /*Definizione del titolo*/

        //Container containerTitle = new Container();
        JPanel containerTitle = new JPanel();
        //containerTitle.setLayout(new GridLayout(1,1));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        containerTitle.setPreferredSize(new Dimension(350, 50));


        containerTitle.add(titleLabel);
        titleLabel.setFont(new Font("Arial", BOLD, 20));
        add(containerTitle);


        /*Definizione box dei messaggi*/
        JPanel containerText = new JPanel();
        //containerText.setLayout(new FlowLayout());
        textArea = new JTextArea();
        textArea.setRows(8);
        textArea.setColumns(35);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", NORMAL, 10));
        textArea.setLineWrap(true);
        //textArea.append("Ciao\n");
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);

        containerText.add(textAreaScrollPane);
        add(containerText);


        JPanel containerInvio = new JPanel();
        //containerInvio.setLayout(new FlowLayout());
        JTextField invioText = new JTextField(20);
        JButton buttonInvio = new JButton("Invio");
        containerInvio.add(invioText);
        containerInvio.add(buttonInvio);
        add(containerInvio);


        buttonInvio.addActionListener(e -> {
            Thread invioThread = null;
            try {
                invioThread = new Thread(new ThreadInvio(socket, invioText.getText()));
                invioThread.start();
                invioThread.interrupt();
                appendMsg(invioText.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });


    }
}

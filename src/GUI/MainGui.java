package GUI;

import ClientChat.ThreadInvio;
import ClientChat.ThreadRicevi;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class MainGui {

    //int port;
    Socket socketRicev;

    public MainGui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("LogIn");
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));


        JPanel panel = new JPanel();
        JTextField text = new JTextField(20);
        JLabel label = new JLabel("Nome");
        JButton button = new JButton("connect");



        frame.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(text);
        panel.add(button);


        button.addActionListener(e -> {connect(text.getText(), frame);});

        frame.setVisible(true);
    }

    private void connect(String text, JFrame frame) {
        frame.dispose();
        //int porta = new Random().nextInt(2000, 9999);
        try {
            Socket socketInvio = new Socket(InetAddress.getLocalHost(), 9876);

            ThreadInvio connect = new ThreadInvio(socketInvio, text);
            Thread thread = new Thread(connect);
            thread.start();
            /*thread.interrupt();
            thread.join();*/
            chat(socketInvio, text);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chat(Socket socketInvio, String text) {

        JFrame frame2 = new JFrame();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Chat");
        frame2.setResizable(false);
        frame2.setSize(500, 500);
        frame2.setLocationRelativeTo(null);

        JLabel titolo = new JLabel("Chat: " + text);
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea textArea = new JTextArea(3,20);
        textArea.setSize(new Dimension(200, 200));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        JScrollPane scrollPane = new JScrollPane(textArea);

        try {
            Thread ricevi = new Thread(new ThreadRicevi(socketInvio, textArea));
            ricevi.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel inputMsg = new JPanel();
        inputMsg.setLayout(new FlowLayout());
        JTextField inputText = new JTextField(20);
        inputText.setFont(new Font("Arial", Font.ITALIC, 15));
        JButton button = new JButton("send");

        button.addActionListener(e -> {
           textArea.append(text+": "+inputText.getText()+";\r\n");

            try {
                Thread invio = new Thread(new ThreadInvio(socketInvio, inputText.getText()));
                invio.start();
                invio.interrupt();
                invio.join();

            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                inputText.setText("");
            }
        });

        inputMsg.add(inputText);
        inputMsg.add(button);

        frame2.add(titolo, BorderLayout.NORTH);
        frame2.add(scrollPane, BorderLayout.CENTER);
        frame2.add(inputMsg, BorderLayout.SOUTH);
        frame2.setVisible(true);
    }

}

package ClientChat;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRicevi implements Runnable{
	private Socket socket;
	 BufferedReader in;
	 private JTextArea textArea;


	public  ThreadRicevi(Socket socket, JTextArea textArea) throws IOException {
		this.socket=socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.textArea = textArea;
	} 
	public void run() {
		String messaggio;
		try {
			messaggio = in.readLine();
		
			while(messaggio!=null){
				System.out.println(messaggio);
				textArea.append(messaggio+";\n");
				messaggio = in.readLine();
			}
			System.out.println("Server Chiuso");
			socket.close();
		} catch (IOException e) {
			System.out.println("Errore di connessione");
		}
	}
}

package ClientChat;

import GUI.ChatGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRicevi implements Runnable{
	private Socket socket;
	 BufferedReader in;
	 private ChatGui chat;

	public  ThreadRicevi(Socket socket, ChatGui chat) throws IOException {
		this.socket=socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.chat=chat;
	} 
	public void run() {
		String messaggio;
		try {
			messaggio = in.readLine();
		
			while(messaggio!=null){
				chat.appendMsg(messaggio);
				System.out.println(messaggio);
				messaggio = in.readLine();
			}
			System.out.println("Server Chiuso");
			socket.close();
		} catch (IOException e) {
			System.out.println("Errore di connessione");
		}
	}
}

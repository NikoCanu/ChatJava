package ClientChat;

import GUI.ChatGui;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
	 public static void main(ChatGui chat){
		 Socket clientSocket;  
		 try {
			clientSocket = new Socket("localhost",5500);
			Thread invioThread = new Thread(new ThreadInvio(clientSocket, "ciao"));
			//Thread riceviThread = new Thread(new ThreadRicevi(clientSocket));
			invioThread.start();
			//riceviThread.start();
		} catch (IOException e) {
			System.out.println("Impossibile connettersi con il server");
		}    
	 }
}


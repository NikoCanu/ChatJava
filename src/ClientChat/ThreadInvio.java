package ClientChat;

import GUI.ChatGui;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadInvio implements Runnable{
	private Scanner sc;
	private PrintWriter out;
	private String message;
	public ThreadInvio (Socket socket, String message) throws IOException {
		sc = new Scanner(System.in);
		out = new PrintWriter(socket.getOutputStream());
		this.message = message;
	}
	public void run() {

		out.println(message);
		out.flush();

	}
}

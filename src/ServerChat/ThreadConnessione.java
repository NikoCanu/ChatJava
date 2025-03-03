package ServerChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnessione implements Runnable {
	private Socket client;
	private BufferedReader in;
	private ListaClient listaClient;
	private String nomeClient;
	
	public ThreadConnessione(Socket client, ListaClient listaClient) throws IOException {
		this.client=client;
		this.listaClient = listaClient;
		in = new BufferedReader (new InputStreamReader(client.getInputStream()));
		nomeClient = "errore";
	}
	public void run() {
		String messaggio;
		boolean primo=true;
		try {
			while(!Thread.interrupted()) {		
				messaggio = in.readLine();
				if(primo) {
					nomeClient=messaggio;
					System.out.println(nomeClient + " Connesso");
					primo=false;
				}else {
					if (messaggio != null) {
						listaClient.sendAll(nomeClient + ": " + messaggio,client);
						System.out.println(nomeClient + ": " + messaggio);
					}
				}			
			}
		} catch (IOException e) {		
			System.out.println("Connessione interrotta con " + nomeClient);
		}
	}	
}

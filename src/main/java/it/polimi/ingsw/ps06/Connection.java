package it.polimi.ingsw.ps06;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import it.polimi.ingsw.ps06.model.messages.Message;
import it.polimi.ingsw.ps06.model.messages.MessageConnection;
import it.polimi.ingsw.ps06.model.messages.MessageParser;
import it.polimi.ingsw.ps06.model.messages.MessageWaitingRoomConnections;

/**
* Classe per la gestione delle singole connessioni al Server.
* Implementazione dell'interfaccia Runnable per la gestione tramite Threads
*
* @author  ps06
* @version 1.0
* @since   2017-06-03 
*/
public class Connection implements Runnable {
	
	private Socket socket;
	
	private String username;
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean active = true;
	
	/**
	 * Costruttore della classe
	 * 
	 * @param socket				Socket del Client di riferimento
	 * @param connectedToServer		Server al quale le connessioni fanno riferimento
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Connection(Socket socket) throws UnknownHostException, IOException {
		this.socket = socket;
		
		this.out = new ObjectOutputStream(socket.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
		
		this.username = "Guest" + (int)(Math.random() * (9999 - 1) + 1);
	}
	
	private synchronized boolean isActive(){
		return active;
	}
	
	@Override
	public void run() {
		try {
			SocketServer.getInstance().rednezvous(this);
			
			while(isActive())
				receive();
						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void receive() throws ClassNotFoundException, IOException {
		MessageConnection m;
		m = (MessageConnection) in.readObject();
		
		System.out.println("[ ] Message received from " + getInetAddress() + " (" + getUsername() + "): " + m);
		
		this.username = m.accept(new MessageParser());
		
		SocketServer.getInstance().sendWaitingConnectionsStats();
	}
	
	/**
	 * Invio di un particolare Messaggio
	 * 
	 * @param message	Messaggio da inviare al Client
	 */
	private void send(Message message) throws IOException {		
		out.writeObject(message);
		out.flush();
	}
	
	
	/**
	 * Gestione Asincrona dell'invio di un messaggio al Client
	 * 
	 * @param message	Messaggio da inviare al Client
	 */
	public void asyncSend(final Message message){
		new Thread(new Runnable() {			
			@Override
			public void run() {
				try {
					send(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}).start();
	}
	
	
	public synchronized void closeConnection() {		
		//send("Connessione terminata!");
		try {
			socket.close();
		} catch (IOException e) {
		}
		active = false;
	}
	
	private void close() {
		closeConnection();		
		System.out.println("Deregistro il client!");
		//server.deregisterConnection(this);
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getInetAddress() {
		return socket.getInetAddress().toString();
	}
}

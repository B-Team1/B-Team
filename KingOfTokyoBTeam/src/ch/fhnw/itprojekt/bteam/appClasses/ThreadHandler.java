package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ThreadHandler extends Thread{
	private ServerInputHandler inputHandler = new ServerInputHandler();
	private Socket socket;
	public ThreadHandler(Socket socket){
		
		this.socket = socket;
	}
	
	public void run(){
		try {			
			while(true){
				synchronized (socket) {
					Message msgIn = Message.receive(socket);
					if(msgIn.getType() == Message.MessageType.Broadcast){
	                 	JOptionPane.showMessageDialog(null, "Gratullation!", "Gratullation", JOptionPane.WARNING_MESSAGE);
	                }
					inputHandler.manageInput(msgIn);		
				}
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}

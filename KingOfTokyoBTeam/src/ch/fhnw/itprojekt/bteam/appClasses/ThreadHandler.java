package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

import javafx.scene.image.ImageView;

import javax.swing.JOptionPane;

public class ThreadHandler extends Thread{

	ConnectionModel connectionModel;
	GameController gamecontroller;

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
					
					inputHandler.manageInput(msgIn);
								
	        		
	        		// luzi switch case mitm chat
	        		switch (msgIn.getType()) {
	    				case Chat:
	    					String text =msgIn.getChat();
		                 	JOptionPane.showMessageDialog(null, "Gratullation!"+ text +"" , "Gratullation", JOptionPane.WARNING_MESSAGE);
		                 	gamecontroller.getInstance();
		                 	gamecontroller.taChat.setEditable(true);
		                 	System.out.println(msgIn.getChat());
		                 	gamecontroller.taChat.setText(msgIn.getChat());
	    					break;
	        		}					
		

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

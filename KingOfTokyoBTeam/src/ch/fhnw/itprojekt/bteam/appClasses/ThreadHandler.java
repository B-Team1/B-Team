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
					try{
						inputHandler.manageInput(msgIn);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
	    	}
		} catch (Exception e) {
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

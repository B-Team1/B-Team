package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

import javafx.scene.image.ImageView;

import javax.swing.JOptionPane;

public class ThreadHandler extends Thread{
	private Socket socket;
	
	public ThreadHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try {			
			while(true){
				synchronized (socket) {
					Message msgIn = Message.receive(socket);
					new ServerInputHandler(msgIn).start();
				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}

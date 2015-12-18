package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import javax.swing.JOptionPane;

public class ThreadHandler extends Thread{
	private Socket socket;
	
	public ThreadHandler(Socket socket){
		this.socket = socket;
	}
	
	/**
	 * Dieser Thread horcht, ob der Server etwas sendet. Wenn er etwas erhält, öffnet er einen neuen Thread welcher den Input abhandelt.
	 * @author Tobias
	 */
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

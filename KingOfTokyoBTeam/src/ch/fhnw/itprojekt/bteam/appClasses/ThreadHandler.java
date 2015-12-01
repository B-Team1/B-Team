package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ThreadHandler extends Thread{
	ConnectionModel connectionModel;
	private Socket socket;
	public ThreadHandler(Socket socket){
		connectionModel = ConnectionModel.getInstance();
		this.socket = socket;
	}
	
	public void run(){
		try {			
			while(true){
				synchronized (socket) {
					Message msgIn = connectionModel.getMsgIn();				
	        		msgIn = Message.receive(socket);
					if(msgIn.getType() == Message.MessageType.Broadcast){
	                 	JOptionPane.showMessageDialog(null, "Gratullation!", "Gratullation", JOptionPane.WARNING_MESSAGE);
	                }
					connectionModel.setMsgIn(msgIn);		
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

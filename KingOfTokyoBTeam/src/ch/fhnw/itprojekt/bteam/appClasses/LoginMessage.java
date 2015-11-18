package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;


@Root
public class LoginMessage {
	
	// Data included in a message
	@Element
	private MessageType type;

	@Element(required = false)
	private String nickname;

	@Element(required = false)
	private String password;
	
	@Element(required = false)
	private boolean checkLogin;

	
	public LoginMessage(MessageType type, String nickname, String password) {
		super();
		this.type = type;
		this.nickname = nickname;
		this.password = password;
	}
	
	
	/**
	 * Receive a message: create a message object and fill it using data transmitted over the given
	 * socket.
	 * 
	 * @param socket
	 *          the socket to read from
	 * @return the new message object, or null in case of an error
	 * @throws Exception
	 */
	public static LoginMessage receive(Socket socket) throws Exception {
		Serializer netIn = new Persister();
		InputStream in = socket.getInputStream();
		LoginMessage msg = netIn.read(LoginMessage.class, in);
		return msg;
	}



	/**
	 * Send the current message.
	 * 
	 * @param socket
	 *          the socket to write to
	 * @throws Exception
	 */
	public void send(Socket socket) throws Exception {
		Serializer netOut = new Persister();
		OutputStream out = socket.getOutputStream();
		netOut.write(this, out);
		out.write('\n');
		out.flush();
	}
	
	/**
	 * Simple string representation
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		StringBuffer msg = new StringBuffer();
		msg.append(this.type.toString());
		msg.append(":");
		if (this.nickname != null) msg.append("  nickname='" + this.nickname + "'");
		if (this.password != null) msg.append("  password='" + this.password + "'");
		return msg.toString();		
	}
	
	

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public MessageType getType() {
		return type;
	}
	
	public boolean getCheckLogin() {
		return checkLogin;
	}

	public void setType(MessageType type) {
		this.type = type;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setCheckLogin(boolean checkLogin) {
		this.checkLogin = checkLogin;
	}
}

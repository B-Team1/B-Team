package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;



/**
 * A simple example showing how to encapsulate messages in a class. This class sends and receives
 * some simple data via sockets. The data is formatted in XML.
 * 
 * Each message is uniquely identified with an ID and a timestamp. This can be useful, for example,
 * if you want to keep a log of messages.
 * 
 * @author brad
 * 
 */
@Root
public class Message {
	public enum MessageType {
		Hello, NewClient, NewClientAccepted, Goodbye, Error, Login, SecurityQuestion, SecurityAnswer, Password
	};

	// Data included in a message
	@Element
	private MessageType type;
	
	@Element(required = false)
	private boolean checkLogin;
	
	@Element(required = false)
	private String nickname;
	
	@Element(required = false)
	private String nName;

	@Element(required = false)
	private String password;
	
	@Element(required = false)
	private String securityQuestion;
	
	@Element(required = false)
	private String securityAnswer;
	
	@Element(required = false)
	private String vName;
	
		
	
	public Message(@Element(name = "type") MessageType type) {
		this.type = type;
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
	public static Message receive(Socket socket) throws Exception {
		Serializer netIn = new Persister();
		InputStream in = socket.getInputStream();
		Message msg = netIn.read(Message.class, in);
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
	
	public String getNickname() {
		return nickname;
	}
	
	public String getNname() {
		return nName;
	}

	public String getPassword() {
		return password;
	}
		
	public boolean getCheckLogin() {
		return checkLogin;
	}

	public MessageType getType() {
		return type;
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

	public void setNname(String nName) {
		this.nName = nName;
	}

	public void setVname(String vName) {
		this.vName = vName;
	}
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	
	public String getVName() {
		return vName;
	}
}

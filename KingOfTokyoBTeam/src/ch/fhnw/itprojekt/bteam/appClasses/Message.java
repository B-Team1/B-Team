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
		Hello, NewClient, NewClientAccepted, Goodbye, Error
	};

	// Data included in a message
	@Attribute
	private long id;

	@Attribute(required = false)
	private long timestamp;

	@Element
	private MessageType type;

	@Element(required = false)
	private String name;

	@Element(required = false)
	private Integer age;

	// Generator for a unique message ID
	private static long messageID = 0;

	/**
	 * Increment the global messageID
	 * 
	 * @return the next valid ID
	 */
	private static long nextMessageID() {
		return messageID++;
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
	 * Constructor to create new messages; the annotation is required to allow the SimpleXML interface
	 * to create objects from XML input.
	 * 
	 * @param type
	 *          the type of the message
	 */
	public Message(@Element(name = "type") MessageType type) {
		this.type = type;
		this.id = nextMessageID();
		this.timestamp = System.currentTimeMillis();
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
		if (this.name != null) msg.append("  name='" + this.name + "'");
		if (this.age != null) msg.append("  age='" + this.age + "'");
		return msg.toString();		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public static long getMessageID() {
		return messageID;
	}

	public long getId() {
		return id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public MessageType getType() {
		return type;
	}
}

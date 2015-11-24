package ch.fhnw.itprojekt.bteam.appClasses;


/**
 * Die Klasse User speichert alle Benutzerinformationen,
 * die nicht mit dem Spielablauf in Verbindung stehen
 * @author Tobias
 *
 */
public class User {
	private String nickname;
	private String vName;
	private String nName;
	private String password;
	private String securityAnswer;
	private String securityQuestion;
	
	public User(String nickname, String vName, String nName, String password, String securityAnswer,
			String securityQuestion) {
		this.nickname = nickname;
		this.vName = vName;
		this.nName = nName;
		this.password = password;
		this.securityAnswer = securityAnswer;
		this.securityQuestion = securityQuestion;
	}
	
	public User(String nickname, String password){
		this.nickname = nickname;
		this.password = password;
	}
	
	public User(String nickname, String nName, String vName){
		this.nickname = nickname;
		this.nName = nName;
		this.vName = vName;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getnName() {
		return nName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
}

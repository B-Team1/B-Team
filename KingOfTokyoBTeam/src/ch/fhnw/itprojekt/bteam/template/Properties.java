package ch.fhnw.itprojekt.bteam.template;

import java.util.Locale;
import java.util.logging.Logger;


public class Properties {
	private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Logger logger = sl.getLogger();
    
	private static Properties properties;
	private Locale locale;
	
	private Properties(){
		this.locale = new Locale("de");
	}
	
	public static Properties getProperties(){
		if(properties == null) {
			properties = new Properties();
		}
		return properties;
	}
	
	public Locale getLocale(){
		return locale;
	}
	
	public void setLocale(Locale locale){
		this.locale = locale;
	}
}

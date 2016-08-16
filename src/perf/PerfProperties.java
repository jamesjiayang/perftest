package perf;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfProperties {

	private static final Logger log = LoggerFactory.getLogger(PerfProperties.class);
	private static PerfProperties perfPropertiesInstance = null;
	private Properties properties = null;  
	
	public static final String PERF_HOME = "perf.home";
	
	private PerfProperties(){
		properties = new Properties();
		properties.putAll(System.getProperties());
	}
	
	public static synchronized PerfProperties getInstance(){
		if(perfPropertiesInstance==null){
			perfPropertiesInstance = new PerfProperties();
		}
		return perfPropertiesInstance;
	}
	
	/**
	 * Return the value of the property as a string
	 * @param propertyName name of the property which holds the value
	 * @return string value or <code>null</code> if the property is not defined.
	 */
	public String getAsString(String propertyName){
		return properties.getProperty(propertyName, null);
	}
	
	/**
	 * Return the value of the property as an integer
	 * @param propertyName name of the property which holds the value
	 * @return integer value or <code>null</code> if the property is not defined or the
	 * value cannot be converted to a number.
	 */
	public Integer getAsInt(String propertyName){
		String propertyStringValue = properties.getProperty(propertyName, null);
		Integer value = null;
		if(propertyStringValue==null){
			log.error("Property not found: '"+propertyName+"'.");
		}else{
			try {
				value = Integer.getInteger(propertyName);
			} catch (NumberFormatException e) {
				log.error("Property '"+propertyName+"' is not a number.");
			} 
		}
		return value;
	}
	
	/**
	 * Return the value of the property as a boolean
	 * @param propertyName name of the property which holds the value
	 * @return boolean value or <code>null</code> if the property is not defined
	 * or if the value cannot be converted to a boolean.
	 */
	public Boolean getAsBoolean(String propertyName){
		String propertyStringValue = properties.getProperty(propertyName, null);
		Boolean value = null;
		if(propertyStringValue==null){
			log.error("Property not found: '"+propertyName+"'.");
			throw new RuntimeException("Property not found: '"+propertyName+"'.");
		}else{
			try {
				value = Boolean.getBoolean(propertyName);
			} catch (NumberFormatException e) {
				log.error("Property '"+propertyName+"' is not a boolean.");
			} 
		}
		return value;
	}

	public boolean isDefined(String propertyName){
		return properties.containsKey(propertyName);
	}
	
	public String getPerfHome(){
		return getAsString(PERF_HOME);
	}

	public static Date getTimeToday(Time time) {
        Calendar cal = Calendar.getInstance();
        
        if (time != null) {
            Calendar tCal = Calendar.getInstance();
            tCal.setTime(time);
            cal.set(Calendar.HOUR_OF_DAY, tCal.get(Calendar.HOUR_OF_DAY));
            cal.set(Calendar.MINUTE, tCal.get(Calendar.MINUTE));
            cal.set(Calendar.SECOND, tCal.get(Calendar.SECOND));
            cal.set(Calendar.MILLISECOND, tCal.get(Calendar.MILLISECOND));
        } else {
            cal.clear(Calendar.HOUR_OF_DAY);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
        }
        
        return cal.getTime();
    }
}

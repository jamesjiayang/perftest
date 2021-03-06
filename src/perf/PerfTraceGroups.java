package perf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Set;


public class PerfTraceGroups {

	private static PerfTraceGroups instance = null;
	private Properties properties = null;  
	
	public static final String OMS_APP_INSTANCE_ID = "OMSAppInstanceID";
	public static final String home = "";
	
	private PerfTraceGroups(){
		
		
		properties = new Properties();
		//String propFileName = configDir+System.getProperty("file.separator")+"brkoms.properties";
		String propFileName = "C:\\dev\\oms_stuff\\PerfTest\\src\\perf\\property.txt";
		File propFile = new File(propFileName);
		FileInputStream fis;
		try {
			fis = new FileInputStream(propFile);
		} catch (FileNotFoundException e) {
			System.out.println("File '"+propFileName+"' not found. Please create the file or change the bns.app.home to point to the right location.");
			throw new RuntimeException("File '"+propFileName+"' not found. Please create the file or change the bns.app.home to point to the right location.");
		} 
		try {
			properties.load(fis);
		} catch (IOException e) {
			System.out.println("Error reading '"+propFileName+"' file."+e.getLocalizedMessage());
			throw new RuntimeException("Error reading '"+propFileName+"' file."+e.getLocalizedMessage());
		}
		properties.putAll(System.getProperties());
	}
	
	public static synchronized PerfTraceGroups getInstance(){
		if(instance==null){
			instance = new PerfTraceGroups();
		}
		return instance;
	}
	
	/**
	 * Return the value of the property as a string
	 * @param propertyName name of the property which holds the value
	 * @return string value or <code>null</code> if the property is not defined.
	 */
	public String getAsString(String propertyName){
		return properties.getProperty(propertyName, null);
	}
	
	public Set<String> getUnitNames() {
		return properties.stringPropertyNames();
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
			System.out.println("Property not found: '"+propertyName+"'.");
		}else{
			try {
				value = Integer.getInteger(propertyName);
			} catch (NumberFormatException e) {
				System.out.println("Property '"+propertyName+"' is not a number.");
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
			System.out.println("Property not found: '"+propertyName+"'.");
			throw new RuntimeException("Property not found: '"+propertyName+"'.");
		}else{
			try {
				value = Boolean.getBoolean(propertyName);
			} catch (NumberFormatException e) {
				System.out.println("Property '"+propertyName+"' is not a boolean.");
			} 
		}
		return value;
	}

	public boolean isDefined(String propertyName){
		return properties.containsKey(propertyName);
	}
	
	public Integer getCurrentInstanceId(){
		return getAsInt(OMS_APP_INSTANCE_ID);
	}

	public String getHome(){
		return getAsString(home);
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

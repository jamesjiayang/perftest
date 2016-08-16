package perf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OMSLogParser {
	
	public static boolean isStep(String stepName, String logEntry) {
		return logEntry != null && 
				logEntry.length()>0 && 
				logEntry.indexOf(stepName)>=0;
	}
	
	public static String getFirstMatch(String regExp, String s) {
		String result = null;
		Pattern pattern = Pattern.compile(regExp);

		Matcher matcher = pattern.matcher(s);

		if (matcher.find()) {
			result = matcher.group();
		}
		return result;
	}
	
	public static Date getTimeStamp(String logEntry) {
		String timestamp = getFirstMatch(Const.TIMESTREG, logEntry);
		Date convertedDate = null;
		try {
			if (timestamp != null) {
				SimpleDateFormat dateFormat = 
					new SimpleDateFormat(Const.TIMESTFORMAT);
				convertedDate = dateFormat.parse(timestamp);
			}
		} catch (ParseException pe) {
			System.out.println(pe);
		}
		return convertedDate;
	}
	
	public static String getThread (String logEntry) {
		return getFirstMatch(Const.THREADREG, logEntry);
	}
	
	public static String getOperationName (String logEntry) {
		return getValue(Const.EQUALSIGN, Const.OPERATION, logEntry);
	}
	
	public static String getDiff (String logEntry) {
		return getValue(Const.EQUALSIGN, Const.DIFF, logEntry);
	}
	
	public static String getValue(String separator, String key, String logEntry) {
		String result = getFirstMatch(key, logEntry);
		return result == null ? null : result.split(separator)[1];
	}
}

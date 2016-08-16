package perf;

public class Const {
	public static final String NEWLINE = "\n";
	public static final String TIMESTREG  = "\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d";
	public static final String TIMESTFORMAT = "HH:mm:ss.SSS";
	/*public static final String TIMESTREG  = "\\d\\d:\\d\\d:\\d\\d";
	public static final String TIMESTFORMAT = "HH:mm:ss";*/
	public static final String TIMERANGEMSEC = "HH:mm:ss";
	public static final String TIMERANGESEC = "HH:mm:ss";
	public static final String TIMERANGEMINUTE = "HH:mm";
	public static final String TIMERANGEHOUR = "HH";
	
	public static final String THREADREG = "\\[http.*:\\d*-\\d*]";
	public static final String DIGITREG = "\\d*";
	public static final String DATEFORMAT = "HH:mm:ss.SSS z";
	//public static final String DATEFORMAT = "HH:mm:ss";
	public static final String OPERATION = "operation=[^,]*";
	public static final String EQUALSIGN = "=";
	public static final String DIFF= "DIFF=[0-9]*";
	public static final String COMMA= ",";
	
	public static final String FORENSICTYPE = "forensic";
	public static final String APPLOG = "applog";
	public static final String DBLOG = "dblog";
	
	public enum TimeRange {
	    MSECOND, SECOND, MINUTE, HOUR, DAY;
	}
}

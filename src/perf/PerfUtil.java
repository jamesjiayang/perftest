package perf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import perf.PerfManager.UnitAndStep;
import perf.PerfDao;

public class PerfUtil {
	public static int INTERVAL = 300000;//milliseconds.
	public static void main(String args[]) {
		try {
			
		    String testType = System.getProperty("type") ;
		    if (null == testType || Const.FORENSICTYPE.equals(testType)) {
		    	String logPath= System.getProperty("logfile");
				if (null == logPath) {
					System.out.println("logfile not specified. " +
						"Usage: PerfUtil -Dlogfile=/path/to/logfile.log " +
						"-Dtype=forensic|applog|dblog");
					System.exit(1);
				}
		    	processForensicLog(logPath);
		    }else if (Const.APPLOG.equals(testType)) {
		    	String logPath= System.getProperty("logfile");
				if (null == logPath) {
					System.out.println("logfile not specified. " +
						"Usage: PerfUtil -Dlogfile=/path/to/logfile.log " +
						"-Dtype=forensic|applog|dblog");
					System.exit(1);
				}
		    	processLog(logPath);
		    }else if (Const.DBLOG.equals(testType)) {
		    	String start = System.getProperty("start");
	    		String end = System.getProperty("end");
	    		if (start == null || end == null) {
	    			System.out.println("start of end time not specified. Usage: " +
	    					"Usage: PerfUtil -Dstart=\"2013/01/28 11:40\" -Dend=\"2013/01/28 12:00\"  " +
	    					"-Dtype=forensic|applog|dblog");
	    			System.exit(1);
	    		}
		    	ApplicationContext context =
		            new ClassPathXmlApplicationContext(new String[] {"config.xml"});
		    	try {
		    		
		            //String start = PerfProperties.getInstance().getAsString("start.time");
		            //String end = PerfProperties.getInstance().getAsString("end.time");
		    		
		            PerfDao dao = (PerfDao)context.getBean("perfDao");
		            List<Map<String, Object>> orderList = dao.initOrderList(start, end);
		            System.out.println("OrderId,EventTime,RoundTrip");
		            for (Map<String, Object> orderMap : orderList) {
		                Long rootOrderId = (Long)orderMap.get("RootOrderId");
		                OrderEventList events = 
		                    new OrderEventList(dao.getOrderEvents(rootOrderId, start, end));
		                events.calculateRoundTrip();
		                System.out.print(events.toRoundTripString());
		            }
		        }catch(Exception ex) {
		            System.out.println(ex.getMessage());
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void processForensicLog(String logPath) throws Exception{
		
	    System.out.println("Analyzing:" + logPath);
	    
		FileInputStream fstream = new FileInputStream(logPath);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String logEntry;
		ForensicUnit fu = new ForensicUnit();
		while ((logEntry = br.readLine()) != null) {
			fu.processLogEntry(logEntry);
		}
		in.close();
		fu.summarize();
		
		System.out.println(fu.toString());
		System.out.println(fu.toTimeRangeString());
	}
	
	public static void processLog(String logPath) throws Exception{
		PerfManager perf = PerfManager.getInstance();
		FileInputStream fstream = new FileInputStream(logPath);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String logEntry;
		
		while ((logEntry = br.readLine()) != null) {
			ArrayList<UnitAndStep> unitAndStepList = perf.findUnitByStep(logEntry);
			boolean isDefinedInMultiUnits = unitAndStepList.size() >1;
			for (int i=0; i<unitAndStepList.size(); i++) {
				UnitAndStep unitAndStep = unitAndStepList.get(i);
				Date ts = OMSLogParser.getTimeStamp(logEntry);
				String thread = OMSLogParser.getThread(logEntry);
				if (unitAndStep.unit != null) {
					unitAndStep.unit.processStep(unitAndStep.stepName, ts, thread, isDefinedInMultiUnits);
				}
			}
		}
		in.close();
		System.out.println(perf.printResult());
	}
}

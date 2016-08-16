package perf;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
public class ForensicUnit{
	protected HashMap<String, ArrayList<Operation>> threadMap;
	protected HashMap<String, ArrayList<Operation>> resultMap;
	protected HashMap<String, OperationResult> calculatedResultMap;
	protected Date startTime;
	
	protected ForensicUnit() {
		threadMap = new HashMap<String, ArrayList<Operation>>();
		resultMap = new HashMap<String, ArrayList<Operation>>();
		calculatedResultMap = new HashMap<String, OperationResult>();
	}
	
	public void processLogEntry(String logEntry) {
		
		String opName = OMSLogParser.getOperationName(logEntry);
		String diffVal = OMSLogParser.getDiff(logEntry);
		Date ts = OMSLogParser.getTimeStamp(logEntry);
		if (this.startTime == null) {
			startTime = ts;
		}
		if (diffVal != null) {
			int diff = Integer.parseInt(diffVal);
			
			ArrayList<Operation> curList = resultMap.get(opName);
			if (curList == null) {
				curList = new ArrayList<Operation>();
				resultMap.put(opName, curList);
			}
			Operation op = new Operation(opName, diff);
			op.setTimeStamp(ts);
			curList.add(op);
		}
	}
	
	private String getDateRangeStart(Date curTime, Const.TimeRange range) {
	    String result = "";
	    if (Const.TimeRange.MSECOND.equals(range)) {
	        result = new SimpleDateFormat(Const.TIMERANGEMSEC).format(curTime);
	    }
	    else if (Const.TimeRange.SECOND.equals(range)) {
            result = new SimpleDateFormat(Const.TIMERANGESEC).format(curTime);
        }
	    if (Const.TimeRange.MINUTE.equals(range)) {
            result = new SimpleDateFormat(Const.TIMERANGEMINUTE).format(curTime);
        }
	    if (Const.TimeRange.HOUR.equals(range)) {
            result = new SimpleDateFormat(Const.TIMERANGEHOUR).format(curTime);
        }
	    return result;
	}
	
	public void summarize() {
		for (String opName : this.resultMap.keySet()) {
			ArrayList<Operation> operations = this.resultMap.get(opName);
			double total = 0;
			int best = Integer.MAX_VALUE;
			int worst = Integer.MIN_VALUE;
			long numExec = 0;
			Map <String, OperationResult> timeRangedMap = new TreeMap<String, OperationResult>();
			
			for (Operation operation: operations) {
				int diff = operation.getDiff();
				
				numExec++;
				
				if (diff < best) {
					best = diff;
				}
				if (diff > worst) {
					worst = diff;
				}
				total += diff;
				
				String curTimeRangeStart =getDateRangeStart(operation.getTimeStamp(), Const.TimeRange.MINUTE);
				
				
				OperationResult curOp = timeRangedMap.get(curTimeRangeStart);
				
				if (curOp == null) {
					curOp = new OperationResult(diff, diff, diff, 0, 1);
					timeRangedMap.put(curTimeRangeStart, curOp);
				}
				else {
					int rangeBest = curOp.getBest();
					if (diff < rangeBest) {
						curOp.setBest(diff);
					}
					int rangeWorst = curOp.getWorst();
					if (diff > rangeWorst) {
						curOp.setWorst(diff);
					}
					long rangeExecNo = curOp.getExecNo()+1;
					double rangeAve = (curOp.getAverage() * curOp.getExecNo() + diff)/rangeExecNo;
					curOp.setAverage(rangeAve);
					curOp.setExecNo(rangeExecNo);
				    timeRangedMap.put(curTimeRangeStart, curOp);
				}
			}
			
			double average = 0;
			long execNo = operations.size();
			if (execNo != 0) {
				average = total / execNo;
			} 
			OperationResult result = new OperationResult(best, worst, average,0, execNo);
			result.setTimeRangedMap(timeRangedMap);
			result.setBest(best);
			result.setAverage(average);
			result.setWorst(worst);
			calculatedResultMap.put(opName, result);
		}
	}
	
	public String toString() {
		String result = ",Average,MIN,MAX,# of Execution"+Const.NEWLINE;
		for (String opName : calculatedResultMap.keySet()) {
			result += opName + Const.COMMA;
			OperationResult opResult = calculatedResultMap.get(opName);
			result += opResult.toString();
		}
		return result;
	}
	
	public String toTimeRangeString() {
	    String result = "";
        for (String opName : calculatedResultMap.keySet()) {
            result += opName + Const.NEWLINE;
            result += ",Range,# of Execution,Average,MIN,MAX,"+Const.NEWLINE;
            OperationResult opResult = calculatedResultMap.get(opName);
            result += opResult.toTimeRangeResultString();
        }
        return result;
	}
}

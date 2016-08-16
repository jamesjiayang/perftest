package perf;

import java.util.Map;


public class OperationResult extends Operation {
	protected int best;
	protected int worst;
	protected double average;
	protected double standDeviation;
	protected long execNo;
	protected Map <String, OperationResult> timeRangedMap;
	
	public int getBest() {
		return best;
	}

	public void setBest(int best) {
		this.best = best;
	}

	public int getWorst() {
		return worst;
	}

	public void setWorst(int worst) {
		this.worst = worst;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getStandDeviation() {
		return standDeviation;
	}

	public void setStandDeviation(double standDeviation) {
		this.standDeviation = standDeviation;
	}

	public long getExecNo() {
		return execNo;
	}

	public void setExecNo(long execNo) {
		this.execNo = execNo;
	}

	
	

	public Map<String, OperationResult> getTimeRangedMap() {
        return timeRangedMap;
    }

    public void setTimeRangedMap(Map<String, OperationResult> timeRangedMap) {
        this.timeRangedMap = timeRangedMap;
    }

    public OperationResult(int best, int worst, double average, double std, long execNo) {
		this.best = best;
		this.worst = worst;
		this.average = average;
		this.standDeviation = std;
		this.execNo = execNo;
	}
	
	public String toString() {
		return new Double(this.average).intValue() + Const.COMMA + this.best + Const.COMMA + 
		       this.worst + Const.COMMA + this.execNo + Const.NEWLINE;
	}
	
	public String toTimeRangeResultString () {
	    StringBuffer buf = new StringBuffer();
	    for (String range : this.timeRangedMap.keySet()) {
	        buf.append(range);
	        buf.append(Const.COMMA);
	        OperationResult result = this.timeRangedMap.get(range);
	        buf.append(result.getExecNo() + Const.COMMA + new Double(result.average).intValue() + Const.COMMA + result.best + Const.COMMA + result.worst + Const.COMMA).
	        	append(Const.NEWLINE);
	    }
	    return buf.toString();
	}
}

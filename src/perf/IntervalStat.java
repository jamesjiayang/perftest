package perf;

public class IntervalStat {
	protected String intervalId;
	protected long execNo;
	public IntervalStat(){}
	public IntervalStat(String intervalId, long execNo) {
		this.intervalId = intervalId;
		this.execNo = execNo;
	}
	
	public String getIntervalId() {
		return intervalId;
	}
	public void setIntervalId(String intervalId) {
		this.intervalId = intervalId;
	}
	public long getExecNo() {
		return execNo;
	}
	public void setExecNo(long execNo) {
		this.execNo = execNo;
	}
	
	
}

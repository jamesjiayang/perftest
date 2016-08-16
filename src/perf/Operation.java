package perf;

public class Operation {
	protected String name;
	protected java.util.Date timeStamp;
	protected boolean isStart;
	protected boolean isEnd;
	protected String threadName;
	protected int diff;
	
	public Operation() {}
	public Operation (String name, int diff) {
		this.name = name;
		this.diff = diff;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDiff() {
		return diff;
	}
	public void setDiff(int diff) {
		this.diff = diff;
	}
	
	public java.util.Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(java.util.Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}

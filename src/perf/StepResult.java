package perf;

public class StepResult extends Step {
	protected long totalTime;
	protected long aveTime;
	protected long maxTime;
	protected long minTime;
	protected long totalExecNo;
	protected int maxNumOfThread;
	
	public StepResult (String stepName, String threadName) {
		super(stepName, threadName);
	}
	public long getTotalExecNo() {
		return totalExecNo;
	}
	public void setTotalExecNo(long totalExecNo) {
		this.totalExecNo = totalExecNo;
	}

	public int getMaxNumOfThread() {
		return maxNumOfThread;
	}
	
	public void setMaxNumOfThread(int numOfThread) {
		if (numOfThread > this.maxNumOfThread) {
			this.maxNumOfThread = numOfThread;
		}
	}
	
	public long getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}
	public long getMintime() {
		return minTime;
	}
	public void setMintime(long mintime) {
		this.minTime = mintime;
	}
	
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public long getAveTime() {
		return aveTime;
	}
	public void setAveTime(long aveTime) {
		this.aveTime = aveTime;
	}
	
	public String toString() {
		double ave = this.totalExecNo == 0 ? 0 : (double)this.totalTime / this.totalExecNo;
		return 	Const.NEWLINE +
				"Step name:" + this.stepName + Const.NEWLINE + 
				"Average time:" + ave + Const.NEWLINE +
				"Total time:" + this.totalTime + Const.NEWLINE +
				"Total # of Execution:" + this.totalExecNo + Const.NEWLINE +
				"Max Thread:" + this.maxNumOfThread + Const.NEWLINE;
	}
}

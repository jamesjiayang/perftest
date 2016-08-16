package perf;

import java.text.SimpleDateFormat;

public class Step {
	
	protected java.util.Date timeStamp;
	protected String stepName;
	protected String stepDescription;
	protected boolean isStartStep;
	protected boolean isEndStep;
	protected String threadName;
	protected double timeAllocation;
	protected int curNumOfThread;

	public Step(){};
	
	public Step(String stepName) {
		this.stepName = stepName;
		this.stepDescription = stepName;
	}
	public Step(String stepName, String threadName) {
		this.stepName = stepName;
		this.stepDescription = stepName;
		this.threadName = threadName;
	}
	
	public int getCurNumOfThread() {
		return curNumOfThread;
	}

	public void setCurNumOfThread(int curNumOfThread) {
		this.curNumOfThread = curNumOfThread;
	}
	
	public String getStepDescription() {
		return stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	public double getTimeAllocation() {
		return timeAllocation;
	}
	public void setTimeAllocation(double timeAllocation) {
		this.timeAllocation = timeAllocation;
	}
	
	public java.util.Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(java.util.Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public boolean isStartStep() {
		return isStartStep;
	}
	public void setStartStep(boolean isStartStep) {
		this.isStartStep = isStartStep;
	}
	public boolean isEndStep() {
		return isEndStep;
	}
	public void setEndStep(boolean isEndStep) {
		this.isEndStep = isEndStep;
	}
	
	public String getStepDecription() {
		return stepDescription;
	}
	public void setStepDecription(String stepDecription) {
		this.stepDescription = stepDecription;
	}

	public String toString () {
		String result = this.stepName;
		SimpleDateFormat text = new SimpleDateFormat(Const.DATEFORMAT);
		result += " timeStamp:" + text.format(this.timeStamp);
		result += " thread:" + this.threadName;
		return result + Const.NEWLINE;
	}
	
	
}

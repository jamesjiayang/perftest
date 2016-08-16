package perf;

public class StepList {
	protected java.util.ArrayList<Step> stepList;
	protected String currentThreadName;
	
	public StepList() {}
	
	public StepList(java.util.ArrayList<Step> stepList,
			String currentThreadName) {
		this.stepList = stepList;
		this.currentThreadName = currentThreadName;
	}
	
	public java.util.ArrayList<Step> getStepList() {
		return stepList;
	}
	public void setStepList(java.util.ArrayList<Step> stepList) {
		this.stepList = stepList;
	}
	public String getCurrentThreadName() {
		return currentThreadName;
	}
	public void setCurrentThreadName(String currentThreadName) {
		this.currentThreadName = currentThreadName;
	}
	
}


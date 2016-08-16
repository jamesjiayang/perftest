package perf;

import java.util.Calendar;
import java.util.Date;

public class Unit {
	//populated in PerfManager when property file is parsed
	protected java.util.ArrayList<Step> stepsTemplate;
	protected java.util.HashMap<String, java.util.ArrayList<Step>> threadMap;
	//contains the final results
	protected java.util.ArrayList<java.util.ArrayList<Step>> resultList;
	protected java.util.ArrayList<Step> homelessSteps;
	protected java.util.ArrayList<java.util.ArrayList<Step>> uncompletedList;
	protected String unitName;
	protected java.util.ArrayList<StepResult> calculatedResult;
	protected java.util.ArrayList<StepResult> badResult;
	protected java.util.ArrayList<StepResult> goodResult;
	
	public Unit() {}
	
	public boolean isFirstStep (String stepName) {
		return this.stepsTemplate != null && 
			stepsTemplate.get(0).getStepName().equals(stepName);
	}
	
	public void setUpcalculatedResultHolder() {
		this.calculatedResult = this.getCalculatedResultHolder();
		this.badResult = this.getCalculatedResultHolder();
	}
	
	public java.util.ArrayList<StepResult> getCalculatedResultHolder() {
		java.util.ArrayList<StepResult> result = new java.util.ArrayList<StepResult>();
		for (int i=0; i<this.stepsTemplate.size(); i++) {
			StepResult stepResult = new StepResult(
				this.stepsTemplate.get(i).getStepName(), 
				this.stepsTemplate.get(i).getThreadName());
			if (i==0) {
				stepResult.setStartStep(true);
			}
			else if (i==this.stepsTemplate.size()-1) {
				stepResult.setEndStep(true);
			}
			result.add(stepResult);
		}
		return result;
	}
	
	
	public Unit(String name) {
		this.unitName = name;
		this.stepsTemplate = new java.util.ArrayList<Step>();
		//this.calculatedResult = new java.util.ArrayList<StepResult>();
		this.threadMap = new java.util.HashMap<String, 
						java.util.ArrayList<Step>>();
		this.resultList = new java.util.ArrayList<java.util.ArrayList<Step>>();
		this.uncompletedList = 
			new java.util.ArrayList<java.util.ArrayList<Step>>();
		this.homelessSteps = new java.util.ArrayList<Step>();
	}
	
	public void addStep(Step step) {
		this.stepsTemplate.add(step);
	}
	
	//calculate and set time allocation
	protected void summarize() {
		long start = Calendar.getInstance().getTime().getTime();
		java.util.Collections.sort(resultList, new StepTimeComparator());
		System.out.println("sort: " + (Calendar.getInstance().getTime().getTime() - start) + " size: " + this.resultList.size());
		System.out.println("****************"+"*****************");
		System.out.println("Unit Name: " + this.unitName);
		int percentage = (int)(this.resultList.size() * 0.1);
		for (int outer = 0; outer < this.resultList.size(); outer++) {
			java.util.ArrayList<Step> result = this.resultList.get(outer);
			for (int inner=0; inner<result.size(); inner++) {
				Step cur = result.get(inner);
				StepResult stepResult = this.calculatedResult.get(inner);
				StepResult pertResult = this.badResult.get(inner);
				stepResult.setMaxNumOfThread(cur.getCurNumOfThread());
				pertResult.setMaxNumOfThread(cur.getCurNumOfThread());
				if (!stepResult.getStepName().equals(cur.getStepName())) {
					String err = "step name different btw result holder:" + 
					stepResult + " and step:" + cur;
					System.out.println(err);
					throw new RuntimeException(err);
				}
				if (inner != 0) {
					Step pre = result.get(inner-1);
					stepResult.setTotalExecNo(stepResult.getTotalExecNo() + 1);
					long lapse = cur.getTimeStamp().getTime() - 
									pre.getTimeStamp().getTime();
					stepResult.setTotalTime(stepResult.getTotalTime() + lapse);
					if (outer <= percentage) {
						pertResult.setTotalExecNo(pertResult.
								getTotalExecNo() + 1);
						pertResult.setTotalTime(pertResult.getTotalTime() 
								+ lapse);
						if (outer < 10) {
							System.out.print(cur);
							if (inner == result.size()-1) {
								System.out.println();
							}
						}
					}
				}
				else {
					if (outer <= percentage && outer < 10) {
						System.out.print(cur);
					}
				}
			}
		}
		
		for (String key : this.threadMap.keySet()) {
			this.uncompletedList.add(this.threadMap.get(key));
		}
	}
	
	public String isDefinedStep(String logEntry) {
		String result = null;
		for (int i=0; i<stepsTemplate.size(); i++) {
			int index = logEntry.indexOf(stepsTemplate.get(i).getStepName());
			if ( index >= 0) {
				result = stepsTemplate.get(i).getStepName();
			}
		}
		return result;
	}
	
	public boolean processStep(String stepName, Date ts, String threadName, boolean isDefinedInMultiUnits) {
		boolean result = false;
		Step step = new Step(stepName, threadName);
		step.setTimeStamp(ts);
		java.util.ArrayList<Step> executedSteps = 
			this.threadMap.get(threadName);
		step.setCurNumOfThread(this.threadMap.size());
		if (executedSteps != null) {
			if (this.stepsTemplate.get(executedSteps.size())
					.getStepName().equals(stepName)) {
				executedSteps.add(step);
				if (this.stepsTemplate.size() == executedSteps.size()) {
					step.setEndStep(true);
					this.threadMap.remove(threadName);
					this.resultList.add(executedSteps);
				}
				result = true;
			}
			else {
				
				if (isDefinedInMultiUnits) {
					this.threadMap.remove(threadName);
				}
				else {
				    //the step is out of order; 
				    //It is possible to miss a homeless step when it is defined in multiple units
				    // and the step can not be found in any of the multiple units, because we are only
				    // recording out-of-order steps when they are *NOT* defined in multiple units.
	                this.homelessSteps.add(step);
				}
			}
		}
		else if (this.stepsTemplate.get(0).getStepName().equals(stepName)) {
			executedSteps = new java.util.ArrayList<Step> ();
			step.setStartStep(true);
			executedSteps.add(step);
			this.threadMap.put(threadName, executedSteps);
			result = true;
		}
		else {
			//this step is out of order, because it is not a start-step
		    // and it does not match the next step of any started steps.
		    this.homelessSteps.add(step);
		}
		return result;
	}
	
	public String toString() {
		String result = this.unitName + Const.NEWLINE;
		result += "------------Calculated Result----------" + Const.NEWLINE;
		for (int i=0; i<this.calculatedResult.size(); i++) {
			result += this.calculatedResult.get(i);
		}
		result += "----End of Calculated Result-----------" + Const.NEWLINE;
		
		result += "------------Top long running Result----" + Const.NEWLINE;
		for (int i=0; i<this.badResult.size(); i++) {
			result += this.badResult.get(i);
		}
		result += "--End of Top long running Result-------" + Const.NEWLINE;
		
		result += "------------Uncompleted steps----------" + Const.NEWLINE;
		result += "Number of uncompleted unit:" 
				+ this.uncompletedList.size() + Const.NEWLINE;
		/*for (int i=0; i<this.uncompletedList.size(); i++) {
			for (int j=0; j<this.uncompletedList.get(i).size(); j++) {
				result += this.uncompletedList.get(i).get(j);
			}
		}*/
		result += "---------End of uncompleted steps------" + Const.NEWLINE;
		
		result += "------------Homeless steps----------" + Const.NEWLINE;
		result += "Number of out of order steps:" 
					+ this.homelessSteps.size() + Const.NEWLINE;
		/*for (int i=0; i<this.homelessSteps.size(); i++) {
			result += this.homelessSteps.get(i);
		}*/
		result += "--------End of Homeless steps-------" + Const.NEWLINE;
		return result + Const.NEWLINE;
	}
}

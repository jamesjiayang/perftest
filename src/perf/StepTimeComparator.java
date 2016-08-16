package perf;

import java.util.Comparator;

public class StepTimeComparator implements 
			Comparator<java.util.ArrayList<Step>> {
	
	public int compare(java.util.ArrayList<Step> o1, 
			java.util.ArrayList<Step> o2) {
		//int result = 0;
		long time1 = o1.get(o1.size()-1).getTimeStamp().getTime() - 
						o1.get(0).getTimeStamp().getTime();
		long time2 = o2.get(o2.size()-1).getTimeStamp().getTime() - 
						o2.get(0).getTimeStamp().getTime();
		
		return (int)(time2 - time1);
	}
}

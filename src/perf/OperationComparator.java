package perf;

import java.util.Comparator;

public class OperationComparator implements 
	Comparator<Operation>{

	public int compare(Operation o1, Operation o2) {
		return o2.getDiff() - o1.getDiff();
	}
}

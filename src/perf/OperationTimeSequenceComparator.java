package perf;
import java.util.Comparator;


public class OperationTimeSequenceComparator implements Comparator<Operation> {
	public int compare(Operation o1, Operation o2) {
		return o1.getTimeStamp().compareTo(o2.getTimeStamp());
	}
}

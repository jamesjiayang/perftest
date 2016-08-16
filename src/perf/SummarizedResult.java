package perf;
import java.util.ArrayList;
public class SummarizedResult {
	protected ArrayList<StepResult> resultList;
	protected String description;
	
	public SummarizedResult() {}
	
	public SummarizedResult(ArrayList<StepResult> resultList, 
			String description) {
		this.resultList = resultList;
		this.description = description;
	}
	
}

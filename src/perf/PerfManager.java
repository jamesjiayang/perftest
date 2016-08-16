package perf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class PerfManager {
	public static java.util.HashMap <String, Unit> units = new java.util.HashMap<String, Unit>();
	protected static PerfManager instance;
	
	private PerfManager() {
		try {
			FileInputStream fstream = new FileInputStream("C:\\dev\\oms_stuff\\PerfTest\\src\\perf\\property.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			// Read File Line By Line
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] traceGroup = line.split("=");
				
				Unit unit = new Unit(traceGroup[0]);
				String steps = traceGroup[1];
				String[] stepNames = steps.split(";");
				for (int i=0; stepNames!=null && i<stepNames.length; i++) {
					Step step = new Step(stepNames[i]);
					if (i==0) {
						step.setStartStep(true);
					}
					else if (i==stepNames.length-1) {
						step.setEndStep(true);
					}
					unit.addStep(step);
				}
				unit.setUpcalculatedResultHolder();
				System.out.println("done adding " + 
						stepNames.length + " steps for unit " + traceGroup[0]);
				units.put(traceGroup[0], unit);
				
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getStackTrace());
		}
		
		//}
	}
	
	
	
	public ArrayList<UnitAndStep> findUnitByStep(String logEntry) {
		ArrayList<UnitAndStep> resultList = new ArrayList<UnitAndStep> ();
		for (Iterator<String> it=
			units.keySet().iterator(); it.hasNext();) {
			Unit curUnit = units.get(it.next());
			String stepName = curUnit.isDefinedStep(logEntry);
			if (stepName != null) {
				//unit = curUnit;
				resultList.add(new UnitAndStep(curUnit, stepName));
			}
		}
		return resultList;
	}
	
	public static PerfManager getInstance() {
		if (instance == null) {
			instance = new PerfManager();
		}
		return instance;
	}
	
	public class UnitAndStep {
		public Unit unit;
		public String stepName;
		public UnitAndStep(Unit unit, String stepName) {
			this.unit = unit;
			this.stepName = stepName;
		}
	}
	
	public String printResult() {
		String result="";
		for (String unitName : units.keySet()) {
			Unit unit = units.get(unitName);
			unit.summarize();
			result += unit.toString();
		}
		return result;
	}
	
}

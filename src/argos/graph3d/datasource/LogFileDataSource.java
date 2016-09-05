package argos.graph3d.datasource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import argos.graph3d.processor.SimpleProcessor;
import argos.graph3d.processor.StepDataProcessor;

public class LogFileDataSource implements DataSource {

	private BufferedReader br;
	private StepDataProcessor processor;
	private StepDataPoint position;
	
	public void init() {
		processor = new SimpleProcessor();
    	try {
			br = new BufferedReader(new FileReader("C:\\Temp\\rawdata.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("IOException: ", e);
			}
	}

	public StepDataPoint getNextStepData() {
	    String line;
		try {
			line = br.readLine();

		    if (line != null) {
		    	String[] items = line.split("\\s+");
		    	float currentTimestamp = Float.parseFloat(items[0]);
		    	float stepInt = Integer.parseInt(items[1]);
		    	float xAccel = Float.parseFloat(items[2]);
		    	float yAccel = Float.parseFloat(items[3]);
		    	float zAccel = Float.parseFloat(items[4]);
		    	boolean footSwitch = StepDataPoint.FOOT_DOWN;
		    	System.out.println("stepInt is " + stepInt);
		    	if (stepInt == 1) {
		    		footSwitch = StepDataPoint.FOOT_DOWN;
		    	} else {
		    		footSwitch = StepDataPoint.FOOT_UP;
		    	}
		    	System.out.println("footSwitch is " + footSwitch);
		    	StepDataPoint accel = new StepDataPoint(xAccel, yAccel, zAccel, currentTimestamp, footSwitch);
		    	StepDataPoint position = processor.process(accel); 
		    	this.position = position;
		    } else {
				throw new RuntimeException("Out of data");

		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("IOException: ", e);
		}
		return this.position;
		
	}
	
	public void destroy() {
	    try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("IOException: ", e);
		}
	}

}

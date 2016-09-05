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
		    	StepDataPoint accel = StepDataPoint.parseFromString(line);
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

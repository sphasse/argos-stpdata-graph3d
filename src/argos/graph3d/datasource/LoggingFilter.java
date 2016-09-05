package argos.graph3d.datasource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import argos.graph3d.processor.SimpleProcessor;
import argos.graph3d.processor.StepDataProcessor;

public class LoggingFilter implements DataSource {
	private DataSource upstream;

	private BufferedWriter br;

	public LoggingFilter(DataSource upstream) {
		this.upstream = upstream;
	}
	
	public void init() {
		upstream.init();
    	try {
			br = new BufferedWriter(new FileWriter("/tmp/stepdata.txt"));
			} catch (IOException e) {
				throw new RuntimeException("IOException: ", e);
			}
	}

	public StepDataPoint getNextStepData() {
		StepDataPoint current = upstream.getNextStepData();

		if (current != null) {
			try {
				br.write(current.toString());
				br.write("\n");
			} catch (IOException e) {
				throw new RuntimeException("IOException: ", e);
			}
		}
		return current;
		
	}
	
	public void destroy() {
	    try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("IOException: ", e);
		}
	    upstream.destroy();
	}

}

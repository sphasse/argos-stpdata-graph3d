package argos.graph3d.processor;

import argos.graph3d.datasource.StepDataPoint;

public interface StepDataProcessor {

	public StepDataPoint process(StepDataPoint accel);
	
	public void init();
	
	public void destroy();
}

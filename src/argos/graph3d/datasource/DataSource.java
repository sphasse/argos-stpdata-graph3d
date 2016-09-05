package argos.graph3d.datasource;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;

public interface DataSource {
	public void init();
	
	public StepDataPoint getNextStepData();

	public void destroy();

}

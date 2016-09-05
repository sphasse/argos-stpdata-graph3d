package argos.graph3d.datasource;

import java.util.Random;

import org.jzy3d.maths.Coord3d;

public class RandomDataSource implements DataSource {

	float x = 0.0F;
	float y = 0.0F;
	float z = 0.0F;
	Random r;

	public void init() {
		x = 0.0F;
		y = 0.0F;
		z = 0.0F;
		r = new Random();
	}
	
	public void destroy() {}


	public StepDataPoint getNextStepData() {

		float dx = 0.05F * (r.nextFloat() - 0.5F);
		float dy = 0.05F * (r.nextFloat() - 0.5F);
		float dz = 0.05F * (r.nextFloat() - 0.5F);

		x = x + dx;
		y = y + dy;
		z = z + dz;
		
		//introduce some delay
		try {
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new StepDataPoint(x, y, z);
	}

}

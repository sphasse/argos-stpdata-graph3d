package argos.graph3d.datasource;

public class StepDataPoint {

public float x;
public float y;
public float z;

public float timestamp;
public boolean footSwitch;
public static boolean FOOT_DOWN = true;
public static boolean FOOT_UP = false;

	public StepDataPoint(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public StepDataPoint(float x, float y, float z, float timestamp, boolean footSwitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.timestamp = timestamp;
		this.footSwitch = footSwitch;
	}
	
	public static StepDataPoint parseFromString(String line) {
		StepDataPoint accel = null;
	    if (line != null) {
	    	//System.out.println("line [" + line + "]");
	    	String[] items = line.split("\\s+");
	    	float currentTimestamp = Float.parseFloat(items[0]);
	    	float stepInt = Integer.parseInt(items[1]);
	    	float xAccel = Float.parseFloat(items[2]);
	    	float yAccel = Float.parseFloat(items[3]);
	    	float zAccel = Float.parseFloat(items[4]);
	    	boolean footSwitch = StepDataPoint.FOOT_DOWN;
	    	if (stepInt == 1) {
	    		footSwitch = StepDataPoint.FOOT_DOWN;
	    	} else {
	    		footSwitch = StepDataPoint.FOOT_UP;
	    	}
	    	accel = new StepDataPoint(xAccel, yAccel, zAccel, currentTimestamp, footSwitch);
	    }
	    return accel;
	}
	
	public String toString() {
		return String.format("%.0f %d %.6f %.6f %.6f", timestamp, footSwitch ? 1 : 0, x, y, z);
	}
}

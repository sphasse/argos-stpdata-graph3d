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
}

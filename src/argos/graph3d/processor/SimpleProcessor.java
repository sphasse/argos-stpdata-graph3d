package argos.graph3d.processor;

import argos.graph3d.datasource.StepDataPoint;

public class SimpleProcessor implements StepDataProcessor {

	private float xCalibrate = 0;
	private float yCalibrate = 0;
	private float zCalibrate = 0;
	private long calibratePoints = 0;

	private boolean previousButton = StepDataPoint.FOOT_DOWN;

	private float previousTimestamp = 0;

	private float xVel = 0;
	private float yVel = 0;
	private float zVel = 0;

	private float xPos = 0;
	private float yPos = 0;
	private float zPos = 0;

	public void init() {
		xCalibrate = 0;
		yCalibrate = 0;
		zCalibrate = 0;
		calibratePoints = 0;
		previousButton = StepDataPoint.FOOT_DOWN;

		previousTimestamp = 0;

		xVel = 0;
		yVel = 0;
		zVel = 0;

		xPos = 0;
		yPos = 0;
		zPos = 0;
	}

	public StepDataPoint process(StepDataPoint accel) {

		boolean currentButton = accel.footSwitch;
		float currentTimestamp = accel.timestamp;


		if (previousButton == StepDataPoint.FOOT_DOWN && currentButton == StepDataPoint.FOOT_UP) {
			// Just started stepping
			calibratePoints = 0;
		}
		if (previousButton == StepDataPoint.FOOT_DOWN && currentButton == StepDataPoint.FOOT_DOWN) {
			// Foot still down
			// Keep averaging readings for calibration
			xCalibrate = ((xCalibrate * calibratePoints) + accel.x) / (calibratePoints + 1);
			yCalibrate = ((yCalibrate * calibratePoints) + accel.y) / (calibratePoints + 1);
			zCalibrate = ((zCalibrate * calibratePoints) + accel.z) / (calibratePoints + 1);
			calibratePoints++;
		}
		if (previousButton == StepDataPoint.FOOT_UP && currentButton == StepDataPoint.FOOT_UP) {
			// Foot still up
			// Integrate velocity
			float timeDelta = (currentTimestamp - previousTimestamp) / 1000000;
			xVel = xVel + ( accel.x - xCalibrate) * timeDelta;
			yVel = yVel + ( accel.y - yCalibrate) * timeDelta;
			zVel = zVel + ( accel.z - zCalibrate) * timeDelta;

			xPos = xPos + xVel * timeDelta;
			yPos = yPos + yVel * timeDelta;
			zPos = zPos + zVel * timeDelta;
		}
		if (previousButton == StepDataPoint.FOOT_UP && currentButton == StepDataPoint.FOOT_DOWN) {
			// just finished stepping
			xCalibrate = accel.x;
			yCalibrate = accel.y;
			zCalibrate = accel.z;
			calibratePoints++;

			// set velocity to zero
			xVel = 0;
			yVel = 0;
			zVel = 0;
		}

		previousTimestamp = currentTimestamp;
		previousButton = currentButton;
		
		return new StepDataPoint(xPos, yPos, zPos, accel.timestamp, accel.footSwitch );

	}

	public float getXPosition() {
		return xPos;
	}

	public float getYPosition() {
		return yPos;
	}

	public float getZPosition() {
		return zPos;
	}
	
	public void destroy() {
	}
}

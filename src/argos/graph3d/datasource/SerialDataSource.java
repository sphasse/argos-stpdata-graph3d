package argos.graph3d.datasource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

import argos.graph3d.processor.SimpleProcessor;
import argos.graph3d.processor.StepDataProcessor;
import purejavacomm.CommPortIdentifier;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.UnsupportedCommOperationException;

public class SerialDataSource implements DataSource {

	InputStream inputStream;
	SerialPort serialPort;
	BufferedReader portReader;
	private StepDataProcessor processor;
	private StepDataPoint position;

	public void init() {
		processor = new SimpleProcessor();

		CommPortIdentifier portId;
		Enumeration portList;
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				System.out.println("Trying serial port: " + portId.getName());
				if (portId.getName().equals("cu.usbmodem1451")) {
					try {
						serialPort = (SerialPort) portId.open("SerialDataSource", 2000);
						portReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
						int baudRate = 115200; // 115200bps
						  serialPort.setSerialPortParams(
						    baudRate,
						    SerialPort.DATABITS_8,
						    SerialPort.STOPBITS_1,
						    SerialPort.PARITY_NONE);
						System.out.println("Opened serial port: " + serialPort);
					} catch (PortInUseException e) {
						// TODO Auto-generated catch block
						throw new RuntimeException("PortInUseException", e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						throw new RuntimeException("IOException", e);
					} catch (UnsupportedCommOperationException e) {
						// TODO Auto-generated catch block
						throw new RuntimeException("UnsupportedCommOperationException", e);
					}

				}
			}
		}
	}

	public StepDataPoint getNextStepData() {
		try {
			String line = portReader.readLine();
			if (line != null) {
				if (!line.startsWith("#") && !line.trim().equals("")) {
					String[] items = line.split("\\s+");
					try {
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
					StepDataPoint accel = new StepDataPoint(xAccel, yAccel, zAccel, currentTimestamp, footSwitch);
					StepDataPoint position = processor.process(accel);
					this.position = position;
					} catch  (NumberFormatException e) {
						System.err.println("invalid data: " + line);
					}
				} else {
					System.out.println("Read comment line:" + line);
				}
			} else {
				throw new RuntimeException("Out of data");

			}
		} catch (IOException e) {
			throw new RuntimeException("IOException", e);
		}
		return this.position;

	}

	public void destroy() {
		serialPort.close();
	}

}

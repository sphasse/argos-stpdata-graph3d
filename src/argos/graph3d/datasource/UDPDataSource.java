package argos.graph3d.datasource;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.jzy3d.maths.Coord3d;

public class UDPDataSource implements DataSource {


	MulticastSocket socket;
	InetAddress group;
	
	public void init() {
		try {
			socket = new MulticastSocket(4446);
			group = InetAddress.getByName("224.67.67.67");
			socket.joinGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public StepDataPoint getNextStepData() {

		DatagramPacket packet;
		    byte[] buf = new byte[256];
		    packet = new DatagramPacket(buf, buf.length);
		    try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    String received = new String(packet.getData());
		    System.out.println("Quote of the Moment: " + received);
		    String[] floatString = received.split("[, ]");

		    float x = Float.parseFloat(floatString[0]);
		    float y = Float.parseFloat(floatString[1]);
		    float z = Float.parseFloat(floatString[2]);
		    
			return new StepDataPoint(x, y, z);


	}
	
	public void destroy() {
		try {
			socket.leaveGroup(group);
			socket.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

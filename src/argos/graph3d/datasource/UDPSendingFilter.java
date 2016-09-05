package argos.graph3d.datasource;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPSendingFilter implements DataSource {
	
	private DataSource upstream;
	private MulticastSocket socket;
	private InetAddress group;
	
	public UDPSendingFilter(DataSource upstream) {
		this.upstream = upstream;
	}
	
	public void init() {
		upstream.init();
		try {
			socket = new MulticastSocket(4446);
			group = InetAddress.getByName("224.67.67.67");
			socket.joinGroup(group);
		} catch (IOException e) {
			throw new RuntimeException("IOException: ", e);
		}
	}

	
	public void destroy() {
		try {
			socket.leaveGroup(group);
			socket.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		upstream.destroy();

	}

	@Override
	public StepDataPoint getNextStepData() {
		StepDataPoint current = upstream.getNextStepData();

		if (current != null) {
			try {
				 String message = current.toString();
				 DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, 4446);
				socket.send(packet);

			} catch (IOException e) {
				throw new RuntimeException("IOException: ", e);
			}
		}
		return current;
	}

}

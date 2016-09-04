package argos.graph3d.datasource;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.jzy3d.maths.Coord3d;

public class UDPDataSender {


	MulticastSocket socket;
	InetAddress group;
	
	public static void main(String[] args) {
		UDPDataSender sender = new UDPDataSender();
		sender.init();
		sender.send("0.5 0.5 0.5");
		sender.send("1 1 1");
		sender.send("1 2 3");
		sender.send("1 4 6");
		sender.send("2.2 3 4");
		
	}
	
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

	public void send(String message) {

		DatagramPacket packet;
		    packet = new DatagramPacket(message.getBytes(), message.length(), group, 4446);

		    try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	}

}

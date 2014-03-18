import java.net.DatagramSocket;
import java.net.SocketAddress;


public class GUIListenThread extends Thread {

	private SocketAddress socketAddress;
	private DatagramSocket socket;

	GUIListenThread(SocketAddress socketAddress, DatagramSocket socket) {
		this.socketAddress = socketAddress;
		this.socket = socket;
	}
		
	public void run()
	{
		
	}


}

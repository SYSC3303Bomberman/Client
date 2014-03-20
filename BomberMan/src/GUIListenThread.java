import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class GUIListenThread extends Thread {

	private DatagramSocket socket;

	GUIListenThread(DatagramSocket socket) {
		this.socket = socket;
	}
		
	public void run()
	{
		
		for(;;){
			byte[] buf = new byte[256];
			DatagramPacket fromServer = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(fromServer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String received = new String(fromServer.getData());
			// put received on some form of IPC.
		}
		
	}


}

import java.io.*;
import java.net.*;
import java.util.*;


public class BomberClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		String received;
		int playerPort;
		InetAddress address; //random change to see if this works....

		if (args.length != 1){
			System.out.println("Usage: java BomberClient <hostname>");
			return;
		}

		do{
				// get a datagram socket.
			DatagramSocket socket = new DatagramSocket();

				// send a request to join the game.
			byte[] buf = new byte[256];
			address = InetAddress.getByName(args[0]);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 10000);
			socket.send(packet);

				// get servers response.
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);

				// convert to string.
			received = new String(packet.getData(), 0, packet.getLength());

			socket.close();

		}while(received.equals("retry"));

		playerPort = Integer.parseInt(received);
		
		clientThread play = new clientThread(playerPort, address);
		play.start();

	}

}

class clientThread extends Thread
{
	
	int playerPort;
	InetAddress address;

	clientThread(int playerPort, InetAddress address)
	{
		this.playerPort = playerPort;
		this.address = address;
	}
	
	public void run()
	{

			// get a datagram socket.
		DatagramSocket socket;
		
			// reads a string from a text file one word at a time and sends it via UDP
		Scanner sc2 = null;
	    try {
	        sc2 = new Scanner(new File("inputs.txt"));
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    while (sc2.hasNextLine()) {
	    	Scanner s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	            String s = s2.next();
	            byte[] buf = new byte[256];
				buf = s.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, address, playerPort);
				try {
					socket = new DatagramSocket();
					socket.send(packet);
					Thread.sleep(500);	// so it's not all over in half a second ;)
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        s2.close();
	    }
		
		
	}
	
}
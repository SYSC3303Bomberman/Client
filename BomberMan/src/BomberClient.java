import java.io.*;
import java.net.*;


public class BomberClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		byte[] received;
		InetAddress address;

		if (args.length != 1){		// Client needs to have a host machine as an input argument
			System.out.println("Usage: java BomberClient <hostname>");
			return;
		}

			// get a datagram socket.
		DatagramSocket socket = new DatagramSocket();  // only need to make 1 socket
		
		/*
		GUIListenThread cheeseBurger = new GUIListenThread(socket);	//can I haz?
		cheeseBurger.start(); // this is the override.
		*/

			// send a request to join the game.
		byte[] buf = new byte[1];
		buf[0] = 0x02;
		address = InetAddress.getByName(args[0]);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 10000);
		socket.send(packet);

			// get servers response.
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		received = packet.getData();
		
		if(received[0]==0x01){		// if NACK
			System.out.println("Server Denied Join Request.");
			socket.close();
			return;
		}else if(received[0]==0x00){	// if ACK
			SocketAddress socketAddress = packet.getSocketAddress();
			clientThread play = new clientThread(socketAddress, socket);
			play.start();
		}else{
			System.out.println("**UNEXPECTED RESPONSE FROM SERVER FOR JOIN**");
			socket.close();
			return;
		}

	}

}


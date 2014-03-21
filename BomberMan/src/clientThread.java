import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Scanner;

class clientThread extends Thread
{
	
	SocketAddress socketAddress;
	DatagramSocket socket;

	clientThread(SocketAddress socketAddress, DatagramSocket socket)
	{
		this.socketAddress = socketAddress;
		this.socket = socket;
	}
	
	public void run()
	{
		
			// reads a string from a text file one word at a time and sends it via UDP
		Scanner sc2 = null;
	    try {
	        sc2 = new Scanner(new File("inputs.txt"));
	        startRequest();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    while (sc2.hasNextLine()) {
	    	Scanner s2 = new Scanner(sc2.nextLine());
	        while (s2.hasNext()) {
	            String s = s2.next();
	            byte[] buf = new byte[1];
				buf = s.getBytes();
				try {
					DatagramPacket packet = new DatagramPacket(buf, buf.length, socketAddress);
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

	private void startRequest() throws IOException {
		
			// send a request to start the game.
		byte[] buf = new byte[1];
		buf[0] = 0x03; 			// eventually this will wait user input... for now we just request start right away
		DatagramPacket packet = new DatagramPacket(buf, buf.length, socketAddress);
		socket.send(packet);
	
			// get servers response.
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		byte[] received = packet.getData();
		
		if(received[0]==0x01){		//if NACK
			System.out.println("Server Denied Start Request.");
			socket.close();
			return;
		}else if(received[0]==0x00){	//if ACK
			
			GUIListenThread cheeseBurger = new GUIListenThread(socket);	//can I haz?
			cheeseBurger.start();
			return;

		}else{
			System.out.println("**UNEXPECTED RESPONSE FROM SERVER FOR START**");
			socket.close();
			return;
		}
		
	}
	
}

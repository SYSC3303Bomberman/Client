import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUIListenThread extends Thread{

	private DatagramSocket socket;
	
	JPanel panel = new JPanel();
	JFrame frame = new JFrame();
	JLabel labels[] = new JLabel[315];
	String received;

	GUIListenThread(DatagramSocket socket) {
		this.socket = socket;
		frame.setSize(800, 700);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new GridLayout(15,21));
		panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		for(int i = 0; i < 315; i++){
			labels[i] = new JLabel("x");
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panel.add(labels[i]);
		}
		frame.add(panel);
		frame.setVisible(true);
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
			received = new String(fromServer.getData());
		}	
	}
}

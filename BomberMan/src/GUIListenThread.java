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
	
	private JPanel panel = new JPanel();
	private JFrame frame = new JFrame();
	private JLabel labels[] = new JLabel[315];
	private String received;
	private char[][] boardView = new char[15][21];

	public GUIListenThread(DatagramSocket socket) {
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
			stringToArray();
			updateGUI();
		}	
	}
	
	private void updateGUI() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 21; i ++)
		{
			for (int j = 0; j < 15; j++)
			{
				labels[21*j+i].setText("" + boardView[j][i]);
			}
		}
		
	}

	synchronized void stringToArray()
	{
		int n = 0, m = 0;
		char read;
		
		for(int i = 0;i<=received.length();i++){
			read = received.charAt(i);
			boardView[n][m] = read;
			if(read =='\n'){
				n=0;
				m++;
			}else if(read == '\0'){
				return;
			}
		}	
	}
}

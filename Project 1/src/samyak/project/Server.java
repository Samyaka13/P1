package samyak.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server  implements ActionListener  {
	JTextField text ;
	JPanel p2;
	static Box vertical = Box.createVerticalBox();
	static JFrame f = new JFrame();
	static DataOutputStream dout;
	Server() throws java.lang.NullPointerException{		
		f.setLayout(null);
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7,94,84));
		p1.setBounds(0,0,450,70);
		f.setUndecorated(true);
 		f.add(p1); 
		p1.setLayout(null);
		f.setSize(450,700);
		f.setLocation(200,50 );
			ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("samyak/project/3.png"));  
			Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
			ImageIcon i3 = new ImageIcon(i2);
		    JLabel back = new JLabel(i3);
		    p1.add(back);
		    back.setBounds(5, 20, 25, 25);
		    back.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e ) {
		    		f.setVisible(false);
		    	}
			});
		ImageIcon profile = new ImageIcon(ClassLoader.getSystemResource("samyak/project/icons/1.png"));
		Image i4 = profile.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i5 = new ImageIcon(i4);
		JLabel j2 = new JLabel(i5);
		p1.add(j2);
	    j2.setBounds(40, 10, 50, 50);
	    ImageIcon video = new ImageIcon(ClassLoader.getSystemResource("samyak/project/icons/video.png"));
	    Image i8 = video.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
	    ImageIcon i9 = new ImageIcon(i8);
	    JLabel j3 = new JLabel(i9);
	    p1.add(j3);
	    j3.setBounds(300, 20, 30, 30);		
		f.setVisible(true);
		ImageIcon phone = new ImageIcon(ClassLoader.getSystemResource("samyak/project/icons/phone.png"));
		Image i10 = phone.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i11 = new ImageIcon(i10);
		JLabel j4 = new JLabel(i11);
		p1.add(j4);
		j4.setBounds(360,20,35,30);
		ImageIcon dots = new ImageIcon(ClassLoader.getSystemResource("samyak/project/icons/3icon.png"));
		Image i12 = dots.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i13 = new ImageIcon(i12);
		JLabel j5 = new JLabel(i13);
		p1.add(j5);
		j5.setBounds(420, 20,10, 25);
		JLabel name = new JLabel("Samyak");
		name.setBounds(110, 15, 100,18);
		name.setForeground(Color.white);
		name.setFont(new Font("SAN_SERIF",Font.BOLD,18)); 
		p1.add(name); 
		JLabel status = new JLabel("Active now");
		status.setBounds(110,35,100,18);
		status.setForeground(Color.GRAY);
		status.setFont(new Font("SAN_SARIF",Font.BOLD,18));
		p1.add(status);
		 p2 = new JPanel();
		p2.setBounds(5,75,440,570);
		f.add(p2);
		 text = new JTextField();
		text.setBounds(5,655,310,40);
		text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		f.add(text);
		JButton send = new JButton("Send");
		send.setVisible(true);
		send.addActionListener(this);
		send.setBounds(320,655,123,40);
		send.setBackground(new Color(7,94,84));
		send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
		f.add(send);
					 
	}
	public static void main(String[] args) {
		Server s1 = new Server();
		try (ServerSocket skt = new ServerSocket(6002)) {
			while(true) {
				 Socket s = skt.accept();
				 DataInputStream din = new DataInputStream(s.getInputStream());
				 dout = new DataOutputStream(s.getOutputStream());
				 while(true) {
					 String msg = din.readUTF();	
					 JPanel panel = formatLabel(msg);
					 JPanel left = new JPanel(new BorderLayout());
					 left.add(panel,BorderLayout.LINE_START);
					 vertical.add(left);
					 f.validate();
				 }
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		String out = text.getText();	
		JPanel p3 = formatLabel(out);
		p2.setLayout(new BorderLayout());
		JPanel right = new JPanel(new BorderLayout());
		right.add(p3,BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createVerticalStrut(15));
		p2.add(vertical,BorderLayout.PAGE_START);
		text.setText("");
		try {
			dout.writeUTF(out);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		f.repaint();
		f.invalidate();
		f.validate();
	}
	public static JPanel formatLabel(String out) {
		JPanel panel =new JPanel();
		 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		 JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
		 output.setBackground(new Color(37,211,102));
		 output.setOpaque(true);
		 output.setFont(new Font("Tahoma",Font.PLAIN,16));
		 output.setBorder(new EmptyBorder(15,15,15,50));
		 panel.add(output);
		 Calendar cal = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 JLabel time = new JLabel();
		 time.setText(sdf.format(cal.getTime()));
		 panel.add(time);
		 return panel;
	}
}

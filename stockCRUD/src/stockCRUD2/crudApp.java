package stockCRUD2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Rectangle;

public class crudApp  {

	public JFrame frmEliko;
	public static crudApp mInstance;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					crudApp window = new crudApp();
					window.frmEliko.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public crudApp() throws IOException, ParseException {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 * @throws IOException 
	 */
	private void initialize() throws IOException, ParseException {
		frmEliko = new JFrame();
		frmEliko.getContentPane().setBounds(new Rectangle(300, 300, 700, 300));
		frmEliko.setBounds(new Rectangle(500, 700, 300, 1000));
		frmEliko.getContentPane().setBackground(Color.GREEN);
		frmEliko.getContentPane().setLayout(null);
		frmEliko.setBackground(Color.RED);
		frmEliko.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Eli\\Desktop\\sum41\\sum41.png"));
		frmEliko.setForeground(Color.YELLOW);
		frmEliko.setFont(new Font("Arial Narrow", Font.PLAIN, 23));
		frmEliko.setTitle("Eliko");
		frmEliko.setBounds(300, 300, 578, 518);
		JTextPane txtpnStop = new JTextPane();
		txtpnStop.setEditable(false);
		txtpnStop.setFont(new Font("Arial", Font.BOLD, 26));
		txtpnStop.setText("Stock Management");
		txtpnStop.setBounds(200, 45, 251, 41);
		frmEliko.getContentPane().add(txtpnStop);
		
		JButton btnNewButton_1_4 = new JButton("soldItems");
		btnNewButton_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					soldItems sItems = new soldItems();
					sItems.setVisible(true);
					sItems.toFront();
					frmEliko.dispose();
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_4.setBounds(262, 141, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4);
		
		JButton btnNewButton_1_4_1 = new JButton("Stock");
		btnNewButton_1_4_1 .addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Stock stock = new Stock();
					stock.frame.setVisible(true);
					
					frmEliko.dispose();
					
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		btnNewButton_1_4_1.setBounds(262, 185, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4_1);
		
		JTextPane txtpnApp = new JTextPane();
		txtpnApp.setEditable(false);
		txtpnApp.setFont(new Font("Arial", Font.BOLD, 26));
		txtpnApp.setText("App");
		txtpnApp.setBounds(281, 86, 58, 41);
		frmEliko.getContentPane().add(txtpnApp);
		
		
	}
	
}

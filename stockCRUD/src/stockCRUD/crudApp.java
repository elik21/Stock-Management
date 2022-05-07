package stockCRUD;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class crudApp {

	private JFrame frmEliko;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					crudApp window = new crudApp();
					window.frmEliko.setVisible(true);
					window.frmEliko.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}

	/**
	 * Create the application.
	 */
	public crudApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEliko = new JFrame();
		frmEliko.getContentPane().setBackground(Color.GREEN);
		frmEliko.getContentPane().setLayout(null);
		
		JTextPane txtpnStop = new JTextPane();
		txtpnStop.setFont(new Font("Arial", Font.BOLD, 26));
		txtpnStop.setText("Stock Management");
		txtpnStop.setBounds(200, 45, 244, 41);
		frmEliko.getContentPane().add(txtpnStop);
		
		JButton btnNewButton_1_4 = new JButton("New button");
		btnNewButton_1_4.setBounds(262, 141, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4);
		
		JButton btnNewButton_1_4_1 = new JButton("New button");
		btnNewButton_1_4_1.setBounds(262, 185, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4_1);
		
		JButton btnNewButton_1_4_2 = new JButton("New button");
		btnNewButton_1_4_2.setBounds(262, 229, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4_2);
		
		JButton btnNewButton_1_4_3 = new JButton("New button");
		btnNewButton_1_4_3.setBounds(262, 273, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4_3);
		
		JButton btnNewButton_1_4_4 = new JButton("New button");
		btnNewButton_1_4_4.setBounds(262, 317, 106, 33);
		frmEliko.getContentPane().add(btnNewButton_1_4_4);
		
		JTextPane txtpnApp = new JTextPane();
		txtpnApp.setFont(new Font("Arial", Font.BOLD, 26));
		txtpnApp.setText("App");
		txtpnApp.setBounds(281, 86, 58, 41);
		frmEliko.getContentPane().add(txtpnApp);
		frmEliko.setBackground(Color.RED);
		frmEliko.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Eli\\Desktop\\sum41\\sum41.png"));
		frmEliko.setForeground(Color.YELLOW);
		frmEliko.setFont(new Font("Arial Narrow", Font.PLAIN, 23));
		frmEliko.setTitle("Eliko");
		frmEliko.setBounds(700, 700, 700, 700);
		frmEliko.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

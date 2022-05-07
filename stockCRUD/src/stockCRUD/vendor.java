package stockCRUD2;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jdatepicker.JDatePicker;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import au.com.bytecode.opencsv.CSVReader;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import javax.swing.JFormattedTextField.AbstractFormatter;
import org.jdatepicker.DateModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
public class vendor {

	private JFrame frame;
	private JTextField NBR_gaskets;
	private JTextField h_plates;
	private JTextField EPDM_gaskets;
	private JTextField l_plates;
	private JTextField po;
	private JTextField textField_6;
	private JTable table;
	private JButton btnDel;
	private JButton btnSearch;
	private JButton btnAdd;
	private JButton btnUpdate;
	private Object[] row;
	private Object[] colomn;
	private JDatePanelImpl date;
	private Properties p;
	private UtilDateModel model;
    private DefaultTableModel dtm; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vendor window = new vendor();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public vendor() throws IOException {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		JOptionPane a= new JOptionPane();
		frame.setBounds(100, 100, 875, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setButtons();
		setTexts();
		setTable();
		save();
		UpdateTableOnFields();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					UpdateTableOnTable();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateTableOnFields();
			}
		});
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				tm.removeRow(table.getSelectedRow());
			}
		});
		table.setName("");
		
		JButton btnClear = new JButton("Clear fields");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTable();
			}
		});
		btnClear.setBounds(66, 424, 103, 30);
		frame.getContentPane().add(btnClear);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		//JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		 model = new UtilDateModel();
	    model.setDate(20,04,2014);
		 date = new JDatePanelImpl((DateModel) model, (Properties) p);
		 JDatePickerImpl dp=new JDatePickerImpl(date, new DateComponentFormatter());
		 date.setBackground(Color.WHITE);
		
		dp.setBounds(64, 33, 185, 18);
		frame.getContentPane().add(dp);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				
				if(model.getDay()==0)
				{
						
				JOptionPane.showMessageDialog(frame, "please enter P.O!");
				}
				else {
					
					row[0]=model.getDay()+"/"+(model.getMonth()+1)+"/"+model.getYear();
					row[1]= h_plates.getText();
					row[2]=l_plates.getText();
					row[3]=NBR_gaskets.getText();
					row[4]=po.getText();
					row[5]=EPDM_gaskets.getText();
					tm.addRow(row);
					exportToCSV(table,
				            "C:\\Users\\Eli\\docs\\Desktop\\Java\\eli.csv");
				}
				
			}
		});
		
		
	}
	@SuppressWarnings("deprecation")
	protected void clearTable() {
		// TODO Auto-generated method stub
		model.setValue(new Date("01/01/70"));
	    h_plates.setText("");
	    l_plates.setText("");
	    NBR_gaskets.setText("");
	    po.setText("");
	    EPDM_gaskets.setText("");
	}

	private void setTable() {
		// TODO Auto-generated method stub
        JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setBounds(272, 10, 587, 414);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		
		table.setBackground(Color.green);
		dtm=new DefaultTableModel();
		Object[] colomn= {"Date","H plates","L plates","NBR gaskets","P.O","EPDM gaskets"};
		 row= new Object[6];
		dtm.setColumnIdentifiers(colomn);
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		//table.setVisible(false);

//		dtm.addRow(row);
//		dtm.addRow(row);
//		dtm.addRow(row);
		//dtm.removeRow(1);
	}

	private void setTexts() {
		// TODO Auto-generated method stub
		JLabel lblNewLabel = new JLabel("Date");
		lblNewLabel.setBounds(10, 46, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		NBR_gaskets = new JTextField();
		NBR_gaskets.setBounds(130, 253, 86, 20);
		frame.getContentPane().add(NBR_gaskets);
		NBR_gaskets.setColumns(10);
		
		h_plates = new JTextField();
		h_plates.setBounds(130, 191, 86, 20);
		frame.getContentPane().add(h_plates);
		h_plates.setColumns(10);
		
		EPDM_gaskets = new JTextField();
		EPDM_gaskets.setBounds(130, 315, 86, 20);
		frame.getContentPane().add(EPDM_gaskets);
		EPDM_gaskets.setColumns(10);
		
		l_plates = new JTextField();
		l_plates.setColumns(10);
		l_plates.setBounds(130, 222, 86, 20);
		frame.getContentPane().add(l_plates);
		
		JLabel lblHPlates = new JLabel("H plates");
		lblHPlates.setBounds(30, 194, 46, 14);
		frame.getContentPane().add(lblHPlates);
		
		JLabel lblLPlates = new JLabel("L plates");
		lblLPlates.setBounds(30, 225, 46, 14);
		frame.getContentPane().add(lblLPlates);
		
		JLabel lblNbrGskets = new JLabel("NBR gaskets");
		lblNbrGskets.setBounds(30, 252, 91, 23);
		frame.getContentPane().add(lblNbrGskets);
		
		JLabel lblNewLabel_1 = new JLabel("Date");
		lblNewLabel_1.setBounds(10, 21, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		po = new JTextField();
		po.setColumns(10);
		po.setBounds(130, 284, 86, 20);
		frame.getContentPane().add(po);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(130, 346, 86, 20);
		frame.getContentPane().add(textField_6);
		
		JLabel lblNewLabel_2 = new JLabel("P.O");
		lblNewLabel_2.setBounds(31, 287, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("EPDM gaskets");
		lblNewLabel_3.setBounds(31, 319, 91, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(30, 349, 76, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		
	}
private void save() throws IOException {
	//CSVReader reader = new CSVReader(new FileReader("eli.csv")); 
	//List list = reader.readAll();
	//System.out.println(colomn[0]);
	// table = new JTable(readCsv( "C:\\Users\\Eli\\docs\\Desktop\\Java\\eli.csv"),colomn);
	InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(csv_file));
    CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
}
static Object[][] readCsv(String file) throws IOException {
    List<Object[]> lines = new ArrayList<>();
    try (BufferedReader r = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = r.readLine()) != null)
            lines.add(line.split(","));
    }
    return lines.toArray(new Object[lines.size()][]);
}
	
    private void setButtons() {

		btnDel = new JButton("Delete");
		btnDel.setBounds(10, 401, 112, 23);
		frame.getContentPane().add(btnDel);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(118, 401, 112, 23);
		frame.getContentPane().add(btnSearch);
		
		btnAdd = new JButton("Add");
		btnAdd.setBounds(10, 377, 112, 23);
		frame.getContentPane().add(btnAdd);
		
		btnUpdate = new JButton("Update");
		
		btnUpdate.setBounds(118, 377, 112, 23);
		frame.getContentPane().add(btnUpdate);
	}
 
  
	private void UpdateTableOnTable() throws ParseException {
    	
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				String dateSel=tm.getValueAt(table.getSelectedRow(), 0).toString();
				String h_platesSel=tm.getValueAt(table.getSelectedRow(), 1).toString();
				String l_plstesSel=tm.getValueAt(table.getSelectedRow(), 2).toString();
				String NBRgasketsSel=tm.getValueAt(table.getSelectedRow(), 3).toString();
				String poSel=tm.getValueAt(table.getSelectedRow(), 4).toString();
				String EPDMgasketsSel=tm.getValueAt(table.getSelectedRow(), 5).toString();
			    model.setValue(new SimpleDateFormat("dd/MM/yy").parse(dateSel));
			    h_plates.setText(h_platesSel);
			    l_plates.setText(l_plstesSel);
			    NBR_gaskets.setText(NBRgasketsSel);
			    po.setText(poSel);
			    EPDM_gaskets.setText(EPDMgasketsSel);
		
    }
    private void UpdateTableOnFields() {
    	DefaultTableModel tm=(DefaultTableModel)table.getModel();
		if(table.getSelectedRowCount()==1) {
			
		    String upDate=model.getValue().toString();
		    String up_h_plates=h_plates.getText();
		    String up_l_plates=l_plates.getText();
		    String up_NBRgaskets= NBR_gaskets.getText();
		    String up_po=po.getText();
		    String up_EPDM_gaskets=EPDM_gaskets.getText();
		    
		    tm.setValueAt(upDate,table.getSelectedRow(), 0);
			tm.setValueAt(up_h_plates,table.getSelectedRow(), 1);
			tm.setValueAt(up_l_plates,table.getSelectedRow(), 2);
			tm.setValueAt(up_NBRgaskets,table.getSelectedRow(), 3);
			tm.setValueAt(up_po,table.getSelectedRow(), 4);
			tm.setValueAt(up_EPDM_gaskets,table.getSelectedRow(), 5);
		   
		}
		
    }
    public static void exportToCSV(JTable table,
            String path) {

        try {

            TableModel model = table.getModel();
            FileWriter csv = new FileWriter(new File(path));

            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(model.getColumnName(i) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    csv.write(model.getValueAt(i, j).toString() + ",");
                }
                csv.write("\n");
            }

            csv.close();
        } catch (IOException e) {
           System.out.println("Error "+e);
        }
    }
}

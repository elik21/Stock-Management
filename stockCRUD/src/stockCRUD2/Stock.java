package stockCRUD2;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.jdatepicker.DateModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;
import java.awt.Font;
public class Stock {

	public JFrame frame;
	
	private JTextField NBR_gaskets;
	private JTextField h_plates;
	private JTextField EPDM_gaskets;
	private JTextField l_plates;
	private JTextField po;
	private JTextField searchList;
	
	private JButton btnDel;
	private JButton btnSearch;
	private JButton btnAdd;
	private JButton btnUpdate;
	 private JButton btnClear;
	 
	private JDatePanelImpl date;
	private Properties p;
	private UtilDateModel model;
	
	private JTable table;
	private Object[] row;
	private Object[] colomn;
    private DefaultTableModel dtm; 
    private JTextField total_h_plts;
    private int selectedRow = -1;
    private JTextField total_l_plts;
    private JTextField NBR_total;
    private JTextField EPDM_total;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_4;
    private JLabel lblNewLabel_6;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stock window = new Stock();
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
	public Stock() throws IOException {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		JOptionPane a= new JOptionPane();
		frame.setBounds(100, 100, 902, 641);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		setButtons();
		setTexts();
		setTable();
		 
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
				try {
					UpdateTableOnFields();
					getSum1();
					exportToCSV(table,
				            "eli.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				tm.removeRow(table.getSelectedRow());
				getSum1();
			}
		});
		table.setName("eli");
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clearfields();
			}
		});
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
              try {
            	search(searchList.getText().toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		});
		
		dpicker();//date picker
	
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				
				if(model.getYear()<=1980)
				{
					JOptionPane.showMessageDialog(frame, "please enter correct year!");
				}
				else 
				{
					if(validate()) {
					row[0]=model.getDay()+"/"+(model.getMonth()+1)+"/"+model.getYear();
					row[1]=po.getText();
					row[2]= h_plates.getText();
					row[3]=l_plates.getText();
					row[4]=NBR_gaskets.getText();
					row[5]=EPDM_gaskets.getText();
					tm.addRow(row);
					exportToCSV(table,
				            "eli.csv");
					}
					else {
						JOptionPane.showMessageDialog(frame, "please enter Number!");
					}
				}
				getSum1();
			}
		});
		
		
		save();
		getSum1();
}
	
	
	private Boolean validate() {
		Boolean b=false;
		Pattern p= Pattern.compile("\\d+$");
		if(p.matcher(po.getText()).matches())
		if(p.matcher(h_plates.getText()).matches())
		if(p.matcher(l_plates.getText()).matches())
		if(p.matcher(NBR_gaskets.getText()).matches())
		if(p.matcher(EPDM_gaskets.getText()).matches()) {
			b=true;
		}
		return b;
	}

private void setTexts() {
		
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
		lblNbrGskets.setBounds(30, 282, 91, 23);
		frame.getContentPane().add(lblNbrGskets);
		
		JLabel lblNewLabel_1 = new JLabel("Date");
		lblNewLabel_1.setBounds(10, 99, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		po = new JTextField();
		po.setColumns(10);
		po.setBounds(130, 284, 86, 20);
		frame.getContentPane().add(po);
		
		searchList = new JTextField();
		searchList.setFont(new Font("Tahoma", Font.PLAIN, 14));
		searchList.setColumns(10);
		searchList.setBounds(118, 422, 112, 30);
		frame.getContentPane().add(searchList);
		
		JLabel lblNewLabel_2 = new JLabel("P.O");
		lblNewLabel_2.setBounds(30, 255, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("EPDM gaskets");
		lblNewLabel_3.setBounds(31, 319, 91, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		
	}
protected void search(String name) throws IOException {
	TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(dtm);
	table.setRowSorter(tr);
	///customer_name.setText("e");
	tr.setRowFilter(RowFilter.regexFilter("^"+name));
	//table.addRowSelectionInterval(0, 0);
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
		    btnClear = new JButton("Clear fields");
			btnClear.setBounds(10, 427, 112, 25);
			frame.getContentPane().add(btnClear);
		}

protected void clearfields() {
		// TODO Auto-generated method stub
		model.setValue(new Date("01/01/70"));
	    h_plates.setText("");
	    l_plates.setText("");
	    NBR_gaskets.setText("");
	    po.setText("");
	    EPDM_gaskets.setText("");
	}
public void dpicker() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		//JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		 model = new UtilDateModel();
	    model.setDate(2014,03,25);
	   
		 date = new JDatePanelImpl((DateModel) model, (Properties) p);
		 JDatePickerImpl dp=new JDatePickerImpl(date, new DateComponentFormatter());
		 date.setBackground(Color.BLUE);
		dp.setTextEditable(true);
		
		dp.setBounds(39, 99, 244, 30);
		frame.getContentPane().add(dp);
		
		JLabel lblNewLabel_5 = new JLabel("Total H plates");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(384, 434, 122, 34);
		frame.getContentPane().add(lblNewLabel_5);
		
		total_h_plts = new JTextField();
		total_h_plts.setText("0");
		total_h_plts.setBounds(549, 444, 96, 19);
		frame.getContentPane().add(total_h_plts);
		total_h_plts.setColumns(10);
		
		total_l_plts = new JTextField();
		total_l_plts.setText("0");
		total_l_plts.setColumns(10);
		total_l_plts.setBounds(549, 473, 96, 19);
		frame.getContentPane().add(total_l_plts);
		
		NBR_total = new JTextField();
		NBR_total.setText("0");
		NBR_total.setColumns(10);
		NBR_total.setBounds(549, 502, 96, 19);
		frame.getContentPane().add(NBR_total);
		
		EPDM_total = new JTextField();
		EPDM_total.setText("0");
		EPDM_total.setColumns(10);
		EPDM_total.setBounds(549, 531, 96, 19);
		frame.getContentPane().add(EPDM_total);
		
		lblNewLabel = new JLabel("Total L plates");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(384, 463, 122, 34);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_4 = new JLabel("Total NBR Gaskets");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(384, 492, 155, 34);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_6 = new JLabel("Total EPDM Gaskets");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(384, 521, 143, 34);
		frame.getContentPane().add(lblNewLabel_6);
	}
private void setTable() {
		// TODO Auto-generated method stub
        JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setBounds(293, 10, 566, 414);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		
		table.setBackground(Color.green);
		dtm=new DefaultTableModel();
		Object[] colomn= {"Date","H plates","L plates","NBR gaskets","P.O","EPDM gaskets"};
		 row= new Object[6];
		dtm.setColumnIdentifiers(colomn);
		table.setModel(dtm);
		scrollPane.setViewportView(table);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//table.setVisible(false);

//		dtm.addRow(row);
//		dtm.addRow(row);
//		dtm.addRow(row);
		//dtm.removeRow(1);
	}
	
@SuppressWarnings("unchecked")
private void save() throws IOException {
	//CSVReader reader = new CSVReader(new FileReader("eli.csv")); 
	//List list = reader.readAll();
	//System.out.println(colomn[0]);
	// table = new JTable(readCsv( "C:\\Users\\Eli\\docs\\Desktop\\Java\\eli.csv"),colomn);
	 File csv_file=new File("eli.csv");
	InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(csv_file));

    org.apache.commons.csv.CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
   List<CSVRecord> records = csvParser.getRecords();
   int lines = records.size();
   try {
  System.out.println(lines);
   }
   catch(IndexOutOfBoundsException e){
   	 System.out.println(" - The record wasn't found." );
   	
   }

      int start=0;
      final ArrayList<String[]> results = new ArrayList<>();
      for(CSVRecord csvRecord:records)
      {
   	   
    	  int count = 0;
    	  
    	  final String[] line = new String[csvRecord.size()];
          for (int i = 0; i < csvRecord.size(); i++) {
              line[i] = csvRecord.get(i);
              System.out.println(line[i]);
          }
       
          dtm.addRow(line);
          
          
          count++;
      }
  
      dtm.removeRow(0);
   
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

private void UpdateTableOnTable() throws ParseException {
    	
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				String dateSel=tm.getValueAt(table.getSelectedRow(), 0).toString();
				String h_platesSel=tm.getValueAt(table.getSelectedRow(), 1).toString();
				String l_plstesSel=tm.getValueAt(table.getSelectedRow(), 2).toString();
				String NBRgasketsSel=tm.getValueAt(table.getSelectedRow(), 3).toString();
				String poSel=tm.getValueAt(table.getSelectedRow(), 4).toString();
				String EPDMgasketsSel=tm.getValueAt(table.getSelectedRow(), 5).toString();
			    model.setValue(new SimpleDateFormat("dd/MM/yyyy").parse(dateSel));
			    h_plates.setText(h_platesSel);
			    l_plates.setText(l_plstesSel);
			    NBR_gaskets.setText(NBRgasketsSel);
			    po.setText(poSel);
			    EPDM_gaskets.setText(EPDMgasketsSel);
			    getSum1();
    }
private void UpdateTableOnFields() throws IOException {
    	DefaultTableModel tm=(DefaultTableModel)table.getModel();
		if(table.getSelectedRowCount()==1) {
			
		    String upDate=model.getValue().toString();
		    String up_h_plates=h_plates.getText();
		    String up_l_plates=l_plates.getText();
		    String up_NBRgaskets= NBR_gaskets.getText();
		    String up_po=po.getText();
		    String up_EPDM_gaskets=EPDM_gaskets.getText();
		    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		    
		    tm.setValueAt(form.format(model.getValue()),table.getSelectedRow(), 0);
			tm.setValueAt(up_h_plates,table.getSelectedRow(), 1);
			tm.setValueAt(up_l_plates,table.getSelectedRow(), 2);
			tm.setValueAt(up_NBRgaskets,table.getSelectedRow(), 3);
			tm.setValueAt(up_po,table.getSelectedRow(), 4);
			tm.setValueAt(up_EPDM_gaskets,table.getSelectedRow(), 5);
		}
		
    }
public static void exportToCSV(JTable table,

                                                   String path) 
{

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

public void getSum1() {
	// TODO Auto-generated method stub
	  
    int sumH = 0;
    int sumL = 0;
    int sumNBR = 0;
    int sumEPDM = 0;
   String PATTERN = "\\d+$";
    Pattern pattern = Pattern.compile(PATTERN);
    for(int i =0;i<table.getRowCount();i++){
    	if((pattern.matcher(table.getValueAt(i, 1).toString()).matches())&&!(table.getValueAt(i, 1).toString().equals("")))
        sumH = sumH +Integer.parseInt(table.getValueAt(i, 1).toString());
    	if(pattern.matcher(table.getValueAt(i, 2).toString()).matches()&&!table.getValueAt(i, 2).toString().equals(""))
            sumL= sumL +Integer.parseInt(table.getValueAt(i, 2).toString());
    	if(pattern.matcher(table.getValueAt(i, 3).toString()).matches()&&!table.getValueAt(i, 3).toString().equals(""))
            sumNBR = sumNBR +Integer.parseInt(table.getValueAt(i, 3).toString());
    	if(pattern.matcher(table.getValueAt(i, 5).toString()).matches()&&!table.getValueAt(i, 5).toString().equals(""))
            sumEPDM = sumEPDM +Integer.parseInt(table.getValueAt(i, 5).toString());
        //here i is the row wise iteration and 2 is the column number of my 
    	
    	
    }
   
    total_h_plts.setText(Integer.toString(sumH));
    total_l_plts.setText(Integer.toString(sumL));
    NBR_total.setText(Integer.toString(sumNBR));
    EPDM_total.setText(Integer.toString(sumEPDM));
}
}

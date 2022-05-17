package stockCRUD2;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jdatepicker.DateModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.awt.Rectangle;

public class soldItems extends JFrame implements sumInterface{

	private JPanel contentPane;
	private JTable table;
	
	private JTextField customer_name;
	private JTextField prog_name;
	private JTextField num_PHE;
	private JTextField h_plates;
	private JTextField l_plates;
	private JTextField plates_material;
	private JTextField gaskets_material;
	
	private JButton btnDelete;
	private JButton menu;
	private JButton addBtn;
	private JButton btnSearch;
	private JButton update_btn;
	private JButton btn_clear;
	
	private JComboBox comboBox;
	
	private UtilDateModel model;
	private JDatePanelImpl datePannel;
	private DefaultTableModel dtm;
	private Object[] row;
	public int count=0;
	private static soldItems frame;
	private JTextField h_plt_count;
	private JTextField l_plt_count;
	private JTextField t_EPDM_H;
	private JTextField t_NBR_H;
	private JLabel lblNewLabel_2;
	private JLabel t1;
	private JLabel lblNewLabel_4;
	private JTextField t_EPDM_L;
	private JTextField t_NBR_L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new soldItems();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public soldItems() throws IOException, ParseException {
		setBounds(new Rectangle(500, 700, 300, 1000));
		setSize(new Dimension(50, 50));
		setTitle("PHE Stock");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Eli\\docs\\Desktop\\my docs\\Project Pics\\PHE.jfif"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1003, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setButtons();
		setText();
		setLabels();
	    dpicker();
	    setTable();
	    setModelsList();
	    
	    table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					UpdateTableOnTable();
					count++;
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	    
	    update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					UpdateTableOnFields();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				exportToCSV(table,
				        "eli-soldItems.csv");
			}
		});
	    btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tm=(DefaultTableModel)table.getModel();
				for(int i=0;i<count;i++)
					if(table.getSelectedRowCount()>0)
					tm.removeRow(table.getSelectedRow());
			    getSum();
				exportToCSV(table,
				        "eli-soldItems.csv");
			}
		});
	    addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			addItem();
			
			}
		});
	    btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
	    btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					search(prog_name.getText());
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		save();
		getSum();
	}
	
public int [] getSum() {
		 int sumH=0;
		 int sumL=0;
		 int sumEH=0;
		 int sumNH=0;
		 int sumEL=0;
		 int sumNL=0;
		 try {
    		
			File csv_file=new File("eli-soldItems.csv");
			InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(csv_file));

			org.apache.commons.csv.CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
	       List<CSVRecord> records = csvParser.getRecords();
	       int i=0;
	       DefaultTableModel tm=(DefaultTableModel)table.getModel();
	       
	       for(CSVRecord r: records) {
	    	   if(i>0) {
	    		   Boolean b=false;
	    			Pattern p= Pattern.compile("\\d+$");
	    			String [] rec= toArray(r);
	    			
	    			Boolean b1=p.matcher(rec[5]).matches();
	    			Boolean b2=p.matcher(rec[6]).matches();
	    			System.out.println("the record is"+rec[6]+" "+b1);
	    			if(b1&&b2)
	    			 {	
	    		       System.out.println("the record is"+rec[6]+" "+p.matcher(rec[5]).matches());
	    		       sumH+=Integer.parseInt(r.get(5));
	    	           sumL+=Integer.parseInt(r.get(6));
	    	       if(r.get(8).equals("EPDM")) {
	    	       sumEH+=(Integer.parseInt(r.get(5))-1);
	    	       sumEL+=(Integer.parseInt(r.get(6))-1);
	    	       }
	    	       if(r.get(8).equals("NBR")) {
	    	       sumNH+=(Integer.parseInt(r.get(5))-1);
	    	       sumNL+=(Integer.parseInt(r.get(6))-1);
	              }
	    			}
	    	   }
	    	i++;
	       }
	       h_plt_count.setText(Integer.toString(sumH));
	       l_plt_count.setText(Integer.toString(sumL));
	       t_NBR_H.setText(Integer.toString(sumNH));
	       t_EPDM_H.setText(Integer.toString(sumEH));
	       t_NBR_L.setText(Integer.toString(sumNL));
	       t_EPDM_L.setText(Integer.toString(sumEL));
	      
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int [] arr= {sumH,sumL,sumNH,sumEH,sumNL,sumEL};
	       return arr;

//	        int sum = 0;
//	        for(int i =0;i<table.getRowCount();i++){
//	        	
//	            sum = sum +Integer.parseInt(table.getValueAt(i, 5).toString());
//	            //here i is the row wise iteration and 2 is the column number of my 
//	        	
//	        	
//	        }
//	        //JlableTotal is where I want to display the total
//	        h_plt_count.setText(Integer.toString(sum));
		
   }
public static String[] toArray(CSVRecord rec) {
    String[] arr = new String[rec.size()];
    int i = 0;
    for (String str : rec) {
        arr[i++] = str;
    }
    return arr;
}
//public Boolean validate1() {
//	Boolean b=false;
//	Pattern p= Pattern.compile("\\d+$");
//	if(p.matcher(po.getText()).matches())
//	if(p.matcher(h_plt_count.getText()).matches())
//	if(p.matcher(l_plt_count.getText()).matches())
//	if(p.matcher(t_NBR_H.getText()).matches())
//	if(p.matcher(t_EPDM_L.getText()).matches()) {
//		b=true;
//	}
//	return b;
//}
	protected void search(String name) throws IOException {
		TableRowSorter<DefaultTableModel> tr=new TableRowSorter<DefaultTableModel>(dtm);
		table.setRowSorter(tr);
		///customer_name.setText("e");
		tr.setRowFilter(RowFilter.regexFilter("^"+name));
	}

	protected void exportToCSV(JTable table, String path) {
		// TODO Auto-generated method stub
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
	

	protected void UpdateTableOnFields() throws IOException {
		// TODO Auto-generated method stub
		DefaultTableModel tm=(DefaultTableModel)table.getModel();
		if(table.getSelectedRowCount()==1) {
			
		    String upDate=model.getValue().toString();
		    String custName_up=customer_name.getText();
		    String prog_name_up=prog_name.getText();
		    String num_PHE_up=num_PHE.getText();
		    Object mod_PHE= comboBox.getSelectedItem();
		    String h_plates_up=h_plates.getText();
		    String l_plates_up=l_plates.getText();
		    String mat_gaskets_up=gaskets_material.getText();
		    String plate_mat_up=plates_material.getText();
		    SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
		    
		    
		    tm.setValueAt(form.format(model.getValue()),table.getSelectedRow(), 0);
			tm.setValueAt(custName_up,table.getSelectedRow(), 1);
			tm.setValueAt(prog_name_up,table.getSelectedRow(), 2);
			tm.setValueAt(num_PHE_up,table.getSelectedRow(), 3);
			tm.setValueAt(mod_PHE,table.getSelectedRow(), 4);
			tm.setValueAt(h_plates_up,table.getSelectedRow(), 5);
			tm.setValueAt(l_plates_up,table.getSelectedRow(), 6);
			tm.setValueAt(plate_mat_up,table.getSelectedRow(), 7);
			tm.setValueAt(mat_gaskets_up,table.getSelectedRow(), 8);
			getSum();
		}
	}

	private void UpdateTableOnTable() throws ParseException {
		// TODO Auto-generated method stub

		DefaultTableModel tm=(DefaultTableModel)table.getModel();
		String dateSel=tm.getValueAt(table.getSelectedRow(), 0).toString();
		String custName_Sel=tm.getValueAt(table.getSelectedRow(), 1).toString();
		String prog_name_Sel=tm.getValueAt(table.getSelectedRow(), 2).toString();
		String Num_PHE_Sel=tm.getValueAt(table.getSelectedRow(), 3).toString();
		String mod_PHE_Sel=tm.getValueAt(table.getSelectedRow(), 4).toString();
		String h_platesSel=tm.getValueAt(table.getSelectedRow(), 5).toString();
		String l_platesSel=tm.getValueAt(table.getSelectedRow(), 6).toString();
		String material_p_sel=tm.getValueAt(table.getSelectedRow(), 7).toString();
		String material_g_sel=tm.getValueAt(table.getSelectedRow(), 8).toString();
		
	    model.setValue(new SimpleDateFormat("dd/MM/yyyy").parse(dateSel));
	    customer_name.setText(custName_Sel);
	    prog_name.setText(prog_name_Sel);
	    num_PHE.setText(Num_PHE_Sel);
	    if(((DefaultComboBoxModel)comboBox.getModel()).getIndexOf(mod_PHE_Sel)>-1)
	       comboBox.setSelectedItem(mod_PHE_Sel);   
	    h_plates.setText(h_platesSel);
	    l_plates.setText(l_platesSel);
	   plates_material.setText(material_p_sel);
	    gaskets_material.setText(material_g_sel);
	    
	}

	public void setButtons() {
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(529, 199, 132, 26);
		contentPane.add(btnDelete);
		menu = new JButton("Menu");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
	            dispose();
	           try {
				crudApp ca= new crudApp();
				ca.frmEliko.setVisible(true);
			} catch (IOException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			}
		});
		menu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		menu.setBounds(50, 20, 132, 26);
		contentPane.add(menu);
		 addBtn = new JButton("Add");
		
		addBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addBtn.setBounds(396, 199, 132, 26);
		contentPane.add(addBtn);
		
		 btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSearch.setBounds(529, 225, 132, 26);
		contentPane.add(btnSearch);
		
	    update_btn = new JButton("Update");
	
		update_btn.setActionCommand("Update");
		update_btn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		update_btn.setBounds(396, 225, 132, 26);
		contentPane.add(update_btn);
		
		btn_clear = new JButton("Clear");
		btn_clear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btn_clear.setBounds(472, 248, 118, 27);
		contentPane.add(btn_clear);
	}
	public void setLabels() {
		JLabel lblNewLabel = new JLabel("Date");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(50, 76, 84, 26);
		contentPane.add(lblNewLabel);
		
		JLabel custName = new JLabel("Name");
		custName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		custName.setBounds(50, 112, 84, 26);
		contentPane.add(custName);
		
		JLabel projName = new JLabel("Project Name");
		projName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		projName.setBounds(50, 148, 132, 26);
		contentPane.add(projName);
		
		JLabel PHENumber = new JLabel("Number of PHE");
		PHENumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PHENumber.setBounds(50, 186, 132, 26);
		contentPane.add(PHENumber);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Model of PHE");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1_2_1.setBounds(50, 222, 155, 26);
		contentPane.add(lblNewLabel_1_2_1);
		
		JLabel noHplates = new JLabel("H plates");
		noHplates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		noHplates.setBounds(388, 50, 140, 26);
		contentPane.add(noHplates);
		
		JLabel plates = new JLabel("Plates Material");
		plates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		plates.setBounds(388, 122, 178, 26);
		contentPane.add(plates);
		
		JLabel noLplates = new JLabel("L plates");
		noLplates.setFont(new Font("Tahoma", Font.PLAIN, 16));
		noLplates.setBounds(388, 86, 155, 26);
		contentPane.add(noLplates);
		
		JLabel EPDgaskets_1 = new JLabel("Gaskets Material");
		EPDgaskets_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		EPDgaskets_1.setBounds(388, 162, 132, 27);
		contentPane.add(EPDgaskets_1);
		
	}
	public void setText(){

		customer_name = new JTextField();
		customer_name.setColumns(10);
		customer_name.setBounds(178, 115, 118, 26);
		contentPane.add(customer_name);
		
		prog_name = new JTextField();
		prog_name.setColumns(10);
		prog_name.setBounds(178, 151, 118, 26);
		contentPane.add(prog_name);
		
		num_PHE = new JTextField();
		num_PHE.setColumns(10);
		num_PHE.setBounds(178, 189, 118, 26);
		contentPane.add(num_PHE);
		
		h_plates = new JTextField();
		h_plates.setColumns(10);
		h_plates.setBounds(563, 50, 118, 26);
		contentPane.add(h_plates);
		
		l_plates = new JTextField();
		l_plates.setColumns(10);
		l_plates.setBounds(563, 92, 118, 26);
		contentPane.add(l_plates);
		
		plates_material = new JTextField();
		plates_material.setColumns(10);
		plates_material.setBounds(563, 128, 118, 26);
		contentPane.add(plates_material);
		
		gaskets_material = new JTextField();
		gaskets_material.setColumns(10);
		gaskets_material.setBounds(563, 165, 118, 26);
		contentPane.add(gaskets_material);
	}
	public void dpicker() throws ParseException {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		//JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		model = new UtilDateModel();
	    model.setDate(1970,00,01);
	    model.setSelected(true);
		datePannel= new JDatePanelImpl((DateModel<?>) model, (Properties) p);
		JDatePickerImpl dpicker = new JDatePickerImpl(datePannel , new DateComponentFormatter());
		dpicker.getJFormattedTextField().setBackground(Color.GREEN);
		dpicker.setBackground(Color.WHITE);
		datePannel.setBounds(171, 50, 171, 56);
		datePannel.setBackground(Color.WHITE);
		//contentPane.add(datePannel);
		dpicker.setBounds(178, 79, 132, 26);
		contentPane.add(dpicker);
	}
	public void setTable() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(100, 100));
		scrollPane.setMaximumSize(new Dimension(32772, 32775));
		scrollPane.setBackground(Color.ORANGE);
		scrollPane.setBounds(25, 311, 937, 282);
		contentPane.add(scrollPane);
		table = new JTable();
		table.setName("");
		table.setBounds(57, 304, 850, 256);
		table.setBackground(Color.green);
		dtm=new DefaultTableModel();
		Object[] colomn= {"Date","Customer Name","Project Name","PHE Number","Model","H Plates","L Plates","Plates Material","Gaskets Material"};
		 row= new Object[9];
		dtm.setColumnIdentifiers(colomn);
		table.setModel(dtm);
		JTableHeader jth=table.getTableHeader();
		
		scrollPane.setViewportView(table);
	
	}
	public void setModelsList() {
		String s1[] = { "Select PHE Model","GL-8", "GC-9", "GL-13", "GC-16", "GC-26", "UFX-26" ,"UFX-42","UFX-51","GC-52"};
		 comboBox = new JComboBox(s1);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox.setBounds(177, 224, 155, 28);
		
		contentPane.add(comboBox);
		
		h_plt_count = new JTextField();
		h_plt_count.setBounds(866, 50, 96, 19);
		contentPane.add(h_plt_count);
		h_plt_count.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Total H plates");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(703, 39, 101, 43);
		contentPane.add(lblNewLabel_1);
		
		l_plt_count = new JTextField();
		l_plt_count.setText("0");
		l_plt_count.setColumns(10);
		l_plt_count.setBounds(866, 95, 96, 19);
		contentPane.add(l_plt_count);
		
		t_EPDM_H = new JTextField();
		t_EPDM_H.setText("0");
		t_EPDM_H.setColumns(10);
		t_EPDM_H.setBounds(866, 128, 96, 19);
		contentPane.add(t_EPDM_H);
		
		t_NBR_H = new JTextField();
		t_NBR_H.setText("0");
		t_NBR_H.setColumns(10);
		t_NBR_H.setBounds(866, 168, 96, 19);
		contentPane.add(t_NBR_H);
		
		lblNewLabel_2 = new JLabel("Total L plates");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(703, 81, 101, 43);
		contentPane.add(lblNewLabel_2);
		
		t1 = new JLabel("Total EPDM gaskets-H");
		t1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		t1.setBounds(703, 117, 165, 43);
		contentPane.add(t1);
		
		lblNewLabel_4 = new JLabel(" Total NBR gaskets-H");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(699, 151, 157, 43);
		contentPane.add(lblNewLabel_4);
		
		t_EPDM_L = new JTextField();
		t_EPDM_L.setText("0");
		t_EPDM_L.setColumns(10);
		t_EPDM_L.setBounds(866, 200, 96, 19);
		contentPane.add(t_EPDM_L);
		
		t_NBR_L = new JTextField();
		t_NBR_L.setText("0");
		t_NBR_L.setColumns(10);
		t_NBR_L.setBounds(866, 246, 96, 19);
		contentPane.add(t_NBR_L);
		
		JLabel lblNewLabel_3_1 = new JLabel("Total EPDM gaskets-L");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3_1.setBounds(703, 192, 165, 43);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_4_1 = new JLabel(" Total NBR gaskets -L");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4_1.setBounds(699, 232, 165, 43);
		contentPane.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_3 = new JLabel("Sold Items");
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 26));
		lblNewLabel_3.setBounds(336, 10, 155, 30);
		contentPane.add(lblNewLabel_3);
	}
	public void addItem() {
		DefaultTableModel tm=(DefaultTableModel)table.getModel();
		System.out.println(comboBox.getSelectedItem());
		if(model.getYear()<=1980||comboBox.getSelectedItem().equals("Select PHE Model"))
		{
			JOptionPane.showMessageDialog(frame, "please enter correct fields!");
		}
		else 
		{
			row[0]=model.getDay()+"/"+(model.getMonth()+1)+"/"+model.getYear();
			row[1]= customer_name.getText();
			row[2]=prog_name.getText();
			row[3]=num_PHE.getText();
			row[4]=comboBox.getSelectedItem();
			row[5]=h_plates.getText();
			row[6]= l_plates.getText();
			row[7]=plates_material.getText();
			row[8]=gaskets_material.getText();
			
			
			tm.addRow(row);
			exportToCSV(table,"eli-soldItems.csv");
			
		}
		
	
	}
	@SuppressWarnings("unchecked")
	private void save() throws IOException {
		//CSVReader reader = new CSVReader(new FileReader("eli.csv")); 
		//List list = reader.readAll();
		//System.out.println(colomn[0]);
		// table = new JTable(readCsv( "C:\\Users\\Eli\\docs\\Desktop\\Java\\eli.csv"),colomn);
		 File csv_file=new File("eli-soldItems.csv");
		InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(csv_file));

	    org.apache.commons.csv.CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
	   List<CSVRecord> records = csvParser.getRecords();
	   int lines = records.size();
	   try {
	  System.out.println(lines-1);
	   }
	   catch(IndexOutOfBoundsException e){
	   	 System.out.println(" - The record wasn't found." );
	   	
	   }

	      int start=0;
	      final ArrayList<String[]> results = new ArrayList<>();
	      int count = 0;
	      for(CSVRecord csvRecord:records)
	      {
	   	   
	    	 
	    	  if(count>0) {
	    	 final String[] line = new String[csvRecord.size()];
	          for (int i = 0; i < csvRecord.size()-1; i++) {
	              line[i] = csvRecord.get(i);
	              System.out.println(line[i]);
	          }
	          dtm.addRow(line);
	    	  }
	         
	          
	          
	          count++;
	      }
	}
    private void clear() {
	    	// TODO Auto-generated method stub
			model.setValue(new Date("01/01/70"));
		    customer_name.setText("");
		    prog_name.setText("");
		   num_PHE.setText("");
		   comboBox.setSelectedItem("Select PHE Model");
		    h_plates.setText("");
		    l_plates.setText("");
		    gaskets_material.setText("");
		    plates_material.setText("");
    }
    public void remove() {
    	 try {
    		 int sum=0;
			File csv_file=new File("eli-soldItems.csv");
			InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(csv_file));

			org.apache.commons.csv.CSVParser csvParser= CSVFormat.DEFAULT.parse(inputStreamReader);
	       List<CSVRecord> records = csvParser.getRecords();
	       for(CSVRecord r: records) {
	    	   
	    		   sum+=Integer.parseInt(r.get(5));
	    	
	       }
	       h_plt_count.setText(Integer.toString(sum));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

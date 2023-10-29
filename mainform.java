import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.awt.EventQueue;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mainform extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	private JTable table;
	public JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					mainform fm= new mainform();
					fm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
	}

	/**
	 * Create the frame.
	 */
	public mainform() {
		setFont(new Font("Segoe UI", Font.BOLD, 12));
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Phonebook Management system");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 515);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Phonebook System");
		lblNewLabel.setBounds(226, 20, 244, 30);
		lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.control);
		panel.setBounds(346, 76, 302, 291);
		panel.setForeground(new Color(0, 0, 0));
		panel.setToolTipText("");
		panel.setBorder((Border) new TitledBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Enter Contact Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Enter Contact Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Enter Contact Details", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 55, 74, 29);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Last Name");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 94, 74, 29);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Contact Number");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3.setBounds(10, 133, 120, 29);
		panel.add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(144, 61, 96, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(144, 100, 96, 19);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(144, 139, 96, 19);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(UIManager.getColor("CheckBox.interiorBackground"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
		            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/phonebook?"+"user=root&password=admin");
					PreparedStatement smt=conn.prepareStatement("INSERT INTO `phonebook`.`contacts` (`f_name`, `l_name`, `phno`) VALUES (?,?,?);");
		            smt.setString(1,textField.getText());
				    smt.setString(2,textField_1.getText());
			        smt.setString(3,textField_2.getText());
					smt.executeUpdate();
					JOptionPane.showMessageDialog(null,"Contact Saved!","Phonebook Management System",JOptionPane.INFORMATION_MESSAGE);
		            Statement smt1=conn.createStatement();
					ResultSet rs=smt.executeQuery("SELECT * FROM phonebook.contacts;");		
					DefaultTableModel model=(DefaultTableModel) table.getModel();
					model.setRowCount(0);
					String fname,lname,phno;
					     while(rs.next())
					     {
						   fname=rs.getString(1);
						   lname=rs.getString(2);
						   phno=rs.getString(3);
						   String[] row= {fname,lname,phno};
						   model.addRow(row);
					     }
		            }
				 catch (SQLException e1) {
				  JOptionPane.showMessageDialog(btnNewButton,e1.getMessage(),"Phonebook Management System",JOptionPane.ERROR_MESSAGE);
				 }
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(10, 199, 85, 21);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Edit");
		btnNewButton_1.setBackground(UIManager.getColor("CheckBox.interiorBackground"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ph=textField.getText();
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/phonebook?"+"user=root&password=admin");
					Statement smt=conn.createStatement();
					String query="SELECT f_name FROM phonebook.contacts where f_name='"+ph+"';";
					ResultSet rs=smt.executeQuery(query);
					while(rs.next())
					{
						if(ph.equals(rs.getString("f_name")))
						{
							PreparedStatement smt1=conn.prepareStatement("Update contacts set f_name=?,l_name=?,phno=? where f_name='"+ph+"'");
							smt1.setString(1, textField.getText());
							smt1.setString(2, textField_1.getText());
							smt1.setString(3, textField_2.getText());
							int i=smt1.executeUpdate();
							JOptionPane.showMessageDialog(btnNewButton_1,"Contact Updated!","Phonebook Management System",JOptionPane.INFORMATION_MESSAGE);
				
							Statement st=conn.createStatement();
							rs=st.executeQuery("SELECT * FROM phonebook.contacts;");		
							DefaultTableModel model=(DefaultTableModel) table.getModel();
							model.setRowCount(0);
							String fname,lname,phno;
							while(rs.next())
							{
								fname=rs.getString(1);
								lname=rs.getString(2);
								phno=rs.getString(3);
								String[] row= {fname,lname,phno};
								model.addRow(row);
							}
						}
					}
	
				}catch(Exception ex1)
				{
					JOptionPane.showMessageDialog(btnNewButton,ex1.getMessage(),"Phonebook Management System",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNewButton_1.setBounds(105, 199, 85, 21);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Delete");
		btnNewButton_2.setBackground(UIManager.getColor("CheckBox.interiorBackground"));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ph=textField_2.getText();
				try {
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/phonebook?"+"user=root&password=admin");
					Statement smt=conn.createStatement();
					String query="SELECT phno FROM phonebook.contacts where phno='"+ph+"';";
					ResultSet rs=smt.executeQuery(query);
					while(rs.next())
					{
						if(ph.equals(rs.getString("phno")))
						{
							String str="Delete FROM phonebook.contacts where phno='"+ph+"';";
							PreparedStatement smt1=conn.prepareStatement(str);
							smt1.executeUpdate();
							JOptionPane.showMessageDialog(btnNewButton_2,"Contact Deleted!","Phonebook Management System",JOptionPane.INFORMATION_MESSAGE);
								textField.setText("");
								textField_1.setText("");
								textField_2.setText("");
 
    								Statement st=conn.createStatement();
									rs=smt.executeQuery("SELECT * FROM phonebook.contacts;");		
									DefaultTableModel model=(DefaultTableModel) table.getModel();
									model.setRowCount(0);
									String fname,lname,phno;
									while(rs.next())
									{
										fname=rs.getString(1);
										lname=rs.getString(2);
										phno=rs.getString(3);
										String[] row= {fname,lname,phno};
										model.addRow(row);
									}
						}
					}
				}catch(Exception ex1)
				{
					JOptionPane.showMessageDialog(btnNewButton,ex1.getMessage(),"Phonebook Management System",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNewButton_2.setBounds(200, 199, 85, 21);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Clear Fields");
		btnNewButton_3.setBackground(UIManager.getColor("CheckBox.interiorBackground"));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				JOptionPane.showMessageDialog(btnNewButton_3,"Cleared!","Phonebook Management System",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton_3.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNewButton_3.setBounds(10, 246, 275, 21);
		panel.add(btnNewButton_3);
		
	    scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 84, 293, 283);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(SystemColor.controlHighlight);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int select=table.getSelectedRow();
				TableModel model=table.getModel();
				textField.setText(model.getValueAt(select,0).toString());
				textField_1.setText(model.getValueAt(select,1).toString());
				textField_2.setText(model.getValueAt(select,2).toString());
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Firstname", "Lastname", "Contact Number"
			}
		));
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(SystemColor.activeCaption);
		separator.setBackground(SystemColor.activeCaption);
		separator.setBounds(330, 72, 23, 307);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.activeCaption);
		separator_1.setBounds(0, 72, 658, 12);
		contentPane.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(SystemColor.activeCaption);
		separator_2.setBackground(SystemColor.activeCaption);
		separator_2.setBounds(0, 377, 658, 21);
		contentPane.add(separator_2);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/phonebook?"+"user=root&password=admin");
			Statement smt=conn.createStatement();
			ResultSet rs=smt.executeQuery("SELECT * FROM phonebook.contacts;");		
			DefaultTableModel model=(DefaultTableModel) table.getModel();
			String fname,lname,phno;
			while(rs.next())
			{
				fname=rs.getString(1);
				lname=rs.getString(2);
				phno=rs.getString(3);
				String[] row= {fname,lname,phno};
				model.addRow(row);
			}
			
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(btnNewButton,ex.getMessage(),"Phonebook Management System",JOptionPane.ERROR_MESSAGE);
		}
	}
}

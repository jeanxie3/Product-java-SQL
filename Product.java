package Projectpractice;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
 public class Product {

	private JFrame frame;
	private JTextField t1;
	private JTextField t2;
	private JTextField t3;
	private JTextField t4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Product window = new Product();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Product() {
		initialize();
		Connect();
	}
	
	Connection con;
	PreparedStatement pst;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/supermarket","root","");
		}catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 666, 538);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 0, 652, 97);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product Registation");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 25));
		lblNewLabel.setBounds(167, 21, 284, 51);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 132, 632, 340);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Product Name");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(41, 28, 126, 15);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Price\r\n");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(41, 107, 109, 15);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Discount\r\n");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(41, 184, 109, 15);
		panel_1.add(lblNewLabel_3);
		
		t1 = new JTextField();
		t1.setBounds(223, 22, 96, 21);
		panel_1.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setBounds(223, 107, 96, 21);
		panel_1.add(t2);
		t2.setColumns(10);
		
		t3 = new JTextField();
		t3.setBounds(223, 184, 96, 21);
		panel_1.add(t3);
		t3.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pname,price,dis;
				
				pname = t1.getText();
				price = t2.getText();
				dis = t3.getText();
				
				try {
					
					pst = con.prepareStatement("insert into products(pname,price,discount) values (?,?,?)");
					pst.setString(1, pname);
					pst.setString(2, price);
					pst.setString(3, dis);
					
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null,"Record Added");
					
					}catch (SQLException e1){
						e1.printStackTrace();
				}
				
		}
		});
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton.setBounds(460, 106, 85, 23);
		panel_1.add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Product ID");
		lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(51, 279, 131, 15);
		panel_1.add(lblNewLabel_4);
		
		t4 = new JTextField();
		t4.setBounds(223, 273, 96, 21);
		panel_1.add(t4);
		t4.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int id = Integer.parseInt(t4.getText());
					
					pst = con.prepareStatement("select * from products where id = ?");
					pst.setInt(1, id);
					
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==false) {
						JOptionPane.showMessageDialog(null, "Product ID not found");
					}
					else {
						String name = rs.getString("pname");
						double price = Integer.parseInt(rs.getString("price"));
						double dis = Integer.parseInt(rs.getString("discount"));
						
						t1.setText(name.trim());
						t2.setText(String.valueOf(price).trim());
						t3.setText(String.valueOf(dis).trim());
					}
				}catch(SQLException ex) {
					ex.printStackTrace();				}
			}
		});
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnNewButton_1.setBounds(460, 278, 85, 23);
		panel_1.add(btnNewButton_1);
	}
}

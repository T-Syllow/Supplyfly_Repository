package supplyfly.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Taskbar;

import javax.swing.JTextField;

import supplyfly.datenbankzugriff.DBAccess;

import javax.swing.JPasswordField;
import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Font;

public class LoginGUI {

	private JFrame frmSupplyfly;
	private JTextField txtField_username;
	private JPasswordField pwdField_password;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frmSupplyfly.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		
		
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();	//initializing..
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSupplyfly = new JFrame();
		frmSupplyfly.setIconImage(Toolkit.getDefaultToolkit().getImage("Users/tommysyllow/eclipse-workspace/SupplyFly/Supplyfly_Logo.jpeg"));
		frmSupplyfly.setTitle("SupplyFly");
		frmSupplyfly.setResizable(false);
		frmSupplyfly.setLocationRelativeTo(null);
		frmSupplyfly.setBounds(100, 100, 260, 160);
		frmSupplyfly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmSupplyfly.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel = new JLabel("SupplyFly");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lbl_username = new JLabel("Username:");
		GridBagConstraints gbc_lbl_username = new GridBagConstraints();
		gbc_lbl_username.anchor = GridBagConstraints.EAST;
		gbc_lbl_username.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_username.gridx = 0;
		gbc_lbl_username.gridy = 2;
		panel.add(lbl_username, gbc_lbl_username);
		
		txtField_username = new JTextField();
		GridBagConstraints gbc_txtField_username = new GridBagConstraints();
		gbc_txtField_username.insets = new Insets(0, 0, 5, 0);
		gbc_txtField_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtField_username.gridx = 1;
		gbc_txtField_username.gridy = 2;
		panel.add(txtField_username, gbc_txtField_username);
		txtField_username.setColumns(10);
		
		JLabel lbl_password = new JLabel("Password:");
		GridBagConstraints gbc_lbl_password = new GridBagConstraints();
		gbc_lbl_password.anchor = GridBagConstraints.EAST;
		gbc_lbl_password.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_password.gridx = 0;
		gbc_lbl_password.gridy = 3;
		panel.add(lbl_password, gbc_lbl_password);
		
		pwdField_password = new JPasswordField();
		GridBagConstraints gbc_pwdField_password = new GridBagConstraints();
		gbc_pwdField_password.insets = new Insets(0, 0, 5, 0);
		gbc_pwdField_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwdField_password.gridx = 1;
		gbc_pwdField_password.gridy = 3;
		panel.add(pwdField_password, gbc_pwdField_password);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtField_username.getText();
				String password = new String(pwdField_password.getPassword());
				DBAccess db = new DBAccess();
				if(db.checkForLogin(username, password) == true) {
					BestellungenUerbersichtGUI bestellungsWindow = new BestellungenUerbersichtGUI();
					bestellungsWindow.main(null);
					frmSupplyfly.dispose();
					
				}else {
					JOptionPane.showMessageDialog(null, "Falscher Benutzername oder Password");
					txtField_username.setText("");
					pwdField_password.setText("");
				}
				
		}
		});
		
		GridBagConstraints gbc_btn_login = new GridBagConstraints();
		gbc_btn_login.gridwidth = 2;
		gbc_btn_login.gridx = 0;
		gbc_btn_login.gridy = 9;
		panel.add(btn_login, gbc_btn_login);
	}

}

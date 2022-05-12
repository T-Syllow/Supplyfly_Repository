package supplyfly.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class BestellungenUerbersichtGUI {

	private JFrame frmSupplyfly;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BestellungenUerbersichtGUI window = new BestellungenUerbersichtGUI();
					window.frmSupplyfly.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
//		supplyfly.datenbankzugriff.DBAccess.getConnectionToDatabase();
//		supplyfly.datenbankzugriff.DBAccess.getAlleBestellung();
	}

	/**
	 * Create the application.
	 */
	public BestellungenUerbersichtGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSupplyfly = new JFrame();
		frmSupplyfly.setTitle("SupplyFly");
		frmSupplyfly.setBounds(100, 100, 450, 300);
		frmSupplyfly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{450, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{99, 29, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		frmSupplyfly.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.anchor = GridBagConstraints.NORTH;
		gbc_tabbedPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frmSupplyfly.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel pnl_tab_Produkte = new JPanel();
		tabbedPane.addTab("Produkte", null, pnl_tab_Produkte, null);
		GridBagLayout gbl_pnl_tab_Produkte = new GridBagLayout();
		gbl_pnl_tab_Produkte.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_pnl_tab_Produkte.rowHeights = new int[]{0, 0, 0};
		gbl_pnl_tab_Produkte.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnl_tab_Produkte.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnl_tab_Produkte.setLayout(gbl_pnl_tab_Produkte);
		
		JLabel lbl_HEAD_produktnummer = new JLabel("Produktnummer");
		lbl_HEAD_produktnummer.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_produktnummer = new GridBagConstraints();
		gbc_lbl_HEAD_produktnummer.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_produktnummer.weightx = 1.0;
		gbc_lbl_HEAD_produktnummer.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HEAD_produktnummer.gridx = 0;
		gbc_lbl_HEAD_produktnummer.gridy = 0;
		pnl_tab_Produkte.add(lbl_HEAD_produktnummer, gbc_lbl_HEAD_produktnummer);
		
		JLabel lbl_HEAD_produktname = new JLabel("Produktname");
		lbl_HEAD_produktname.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_produktname = new GridBagConstraints();
		gbc_lbl_HEAD_produktname.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_produktname.weightx = 1.0;
		gbc_lbl_HEAD_produktname.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HEAD_produktname.gridx = 1;
		gbc_lbl_HEAD_produktname.gridy = 0;
		pnl_tab_Produkte.add(lbl_HEAD_produktname, gbc_lbl_HEAD_produktname);
		
		JLabel lbl_HEAD_produktpreis = new JLabel("Produktpreis");
		lbl_HEAD_produktpreis.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_produktpreis = new GridBagConstraints();
		gbc_lbl_HEAD_produktpreis.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_produktpreis.weightx = 1.0;
		gbc_lbl_HEAD_produktpreis.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HEAD_produktpreis.gridx = 2;
		gbc_lbl_HEAD_produktpreis.gridy = 0;
		pnl_tab_Produkte.add(lbl_HEAD_produktpreis, gbc_lbl_HEAD_produktpreis);
		
		JLabel lbl_HEAD_standardlieferant = new JLabel("Standardlieferant");
		lbl_HEAD_standardlieferant.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_standardlieferant = new GridBagConstraints();
		gbc_lbl_HEAD_standardlieferant.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_standardlieferant.weightx = 1.0;
		gbc_lbl_HEAD_standardlieferant.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_HEAD_standardlieferant.gridx = 3;
		gbc_lbl_HEAD_standardlieferant.gridy = 0;
		pnl_tab_Produkte.add(lbl_HEAD_standardlieferant, gbc_lbl_HEAD_standardlieferant);
		
		JPanel pnl_produktnummerListed = new JPanel();
		GridBagConstraints gbc_pnl_produktnummerListed = new GridBagConstraints();
		gbc_pnl_produktnummerListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_produktnummerListed.insets = new Insets(0, 0, 0, 5);
		gbc_pnl_produktnummerListed.gridx = 0;
		gbc_pnl_produktnummerListed.gridy = 1;
		pnl_tab_Produkte.add(pnl_produktnummerListed, gbc_pnl_produktnummerListed);
		pnl_produktnummerListed.setLayout(new BoxLayout(pnl_produktnummerListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		pnl_produktnummerListed.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("New label");
		pnl_produktnummerListed.add(lblNewLabel_9);
		
		JPanel pnl_produktnamenListed = new JPanel();
		GridBagConstraints gbc_pnl_produktnamenListed = new GridBagConstraints();
		gbc_pnl_produktnamenListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_produktnamenListed.insets = new Insets(0, 0, 0, 5);
		gbc_pnl_produktnamenListed.gridx = 1;
		gbc_pnl_produktnamenListed.gridy = 1;
		pnl_tab_Produkte.add(pnl_produktnamenListed, gbc_pnl_produktnamenListed);
		pnl_produktnamenListed.setLayout(new BoxLayout(pnl_produktnamenListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_10 = new JLabel("New label");
		pnl_produktnamenListed.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("New label");
		pnl_produktnamenListed.add(lblNewLabel_11);
		
		JPanel pnl_produktpreisListed = new JPanel();
		GridBagConstraints gbc_pnl_produktpreisListed = new GridBagConstraints();
		gbc_pnl_produktpreisListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_produktpreisListed.insets = new Insets(0, 0, 0, 5);
		gbc_pnl_produktpreisListed.gridx = 2;
		gbc_pnl_produktpreisListed.gridy = 1;
		pnl_tab_Produkte.add(pnl_produktpreisListed, gbc_pnl_produktpreisListed);
		pnl_produktpreisListed.setLayout(new BoxLayout(pnl_produktpreisListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_12 = new JLabel("New label");
		pnl_produktpreisListed.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("New label");
		pnl_produktpreisListed.add(lblNewLabel_13);
		
		JPanel pnl_standardlieferantListed = new JPanel();
		GridBagConstraints gbc_pnl_standardlieferantListed = new GridBagConstraints();
		gbc_pnl_standardlieferantListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_standardlieferantListed.gridx = 3;
		gbc_pnl_standardlieferantListed.gridy = 1;
		pnl_tab_Produkte.add(pnl_standardlieferantListed, gbc_pnl_standardlieferantListed);
		pnl_standardlieferantListed.setLayout(new BoxLayout(pnl_standardlieferantListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_14 = new JLabel("New label");
		pnl_standardlieferantListed.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("New label");
		pnl_standardlieferantListed.add(lblNewLabel_15);
		
		JPanel pnl_tab_Lieferanten = new JPanel();
		tabbedPane.addTab("Lieferanten", null, pnl_tab_Lieferanten, null);
		
		JPanel pnl_tab_Bestellung = new JPanel();
		tabbedPane.addTab("Bestellung", null, pnl_tab_Bestellung, null);
		GridBagLayout gbl_pnl_tab_Bestellung = new GridBagLayout();
		gbl_pnl_tab_Bestellung.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnl_tab_Bestellung.rowHeights = new int[]{0, 0, 0};
		gbl_pnl_tab_Bestellung.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_pnl_tab_Bestellung.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		pnl_tab_Bestellung.setLayout(gbl_pnl_tab_Bestellung);
		
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.gridwidth = 7;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		pnl_tab_Bestellung.add(table, gbc_table);
		
		JButton btnNewButton = new JButton("Bestellung hinzufügen");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 1;
		frmSupplyfly.getContentPane().add(btnNewButton, gbc_btnNewButton);
	}

}

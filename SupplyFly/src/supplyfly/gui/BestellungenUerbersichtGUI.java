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

public class BestellungenUerbersichtGUI {

	private JFrame frmSupplyfly;

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
		frmSupplyfly.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmSupplyfly.getContentPane().add(tabbedPane, BorderLayout.NORTH);
		
		JPanel pnl_tab_Bestellungen = new JPanel();
		pnl_tab_Bestellungen.setToolTipText("");
		tabbedPane.addTab("Bestellungen", null, pnl_tab_Bestellungen, null);
		GridBagLayout gbl_pnl_tab_Bestellungen = new GridBagLayout();
		gbl_pnl_tab_Bestellungen.columnWidths = new int[]{0, 0, 0, 0, 2};
		gbl_pnl_tab_Bestellungen.rowHeights = new int[]{0, 0, 0, 2};
		gbl_pnl_tab_Bestellungen.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnl_tab_Bestellungen.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		pnl_tab_Bestellungen.setLayout(gbl_pnl_tab_Bestellungen);
		
		JLabel lbl_HEAD_Bestell_id = new JLabel("Bestellung");
		lbl_HEAD_Bestell_id.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_Bestell_id = new GridBagConstraints();
		gbc_lbl_HEAD_Bestell_id.weightx = 1.0;
		gbc_lbl_HEAD_Bestell_id.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_Bestell_id.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HEAD_Bestell_id.gridx = 0;
		gbc_lbl_HEAD_Bestell_id.gridy = 0;
		pnl_tab_Bestellungen.add(lbl_HEAD_Bestell_id, gbc_lbl_HEAD_Bestell_id);
		
		JLabel lbl_HEAD_Datum = new JLabel("Datum");
		lbl_HEAD_Datum.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_Datum = new GridBagConstraints();
		gbc_lbl_HEAD_Datum.weightx = 1.0;
		gbc_lbl_HEAD_Datum.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_Datum.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HEAD_Datum.gridx = 1;
		gbc_lbl_HEAD_Datum.gridy = 0;
		pnl_tab_Bestellungen.add(lbl_HEAD_Datum, gbc_lbl_HEAD_Datum);
		
		JLabel lbl_HEAD_Mitarbeiter = new JLabel("Mitarbeiter");
		lbl_HEAD_Mitarbeiter.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_Mitarbeiter = new GridBagConstraints();
		gbc_lbl_HEAD_Mitarbeiter.weightx = 1.0;
		gbc_lbl_HEAD_Mitarbeiter.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_Mitarbeiter.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HEAD_Mitarbeiter.gridx = 2;
		gbc_lbl_HEAD_Mitarbeiter.gridy = 0;
		pnl_tab_Bestellungen.add(lbl_HEAD_Mitarbeiter, gbc_lbl_HEAD_Mitarbeiter);
		
		JLabel lbl_HEAD_Bestellart = new JLabel("Bestellart");
		lbl_HEAD_Bestellart.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_lbl_HEAD_Bestellart = new GridBagConstraints();
		gbc_lbl_HEAD_Bestellart.weightx = 1.0;
		gbc_lbl_HEAD_Bestellart.anchor = GridBagConstraints.NORTHWEST;
		gbc_lbl_HEAD_Bestellart.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_HEAD_Bestellart.gridx = 3;
		gbc_lbl_HEAD_Bestellart.gridy = 0;
		pnl_tab_Bestellungen.add(lbl_HEAD_Bestellart, gbc_lbl_HEAD_Bestellart);
		
		JPanel pnl_Bestell_idsListed = new JPanel();
		GridBagConstraints gbc_pnl_Bestell_idsListed = new GridBagConstraints();
		gbc_pnl_Bestell_idsListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_Bestell_idsListed.insets = new Insets(0, 0, 5, 5);
		gbc_pnl_Bestell_idsListed.gridx = 0;
		gbc_pnl_Bestell_idsListed.gridy = 1;
		pnl_tab_Bestellungen.add(pnl_Bestell_idsListed, gbc_pnl_Bestell_idsListed);
		pnl_Bestell_idsListed.setLayout(new BoxLayout(pnl_Bestell_idsListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		pnl_Bestell_idsListed.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		pnl_Bestell_idsListed.add(lblNewLabel);
		
		JPanel pnl_datumListed = new JPanel();
		GridBagConstraints gbc_pnl_datumListed = new GridBagConstraints();
		gbc_pnl_datumListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_datumListed.insets = new Insets(0, 0, 5, 5);
		gbc_pnl_datumListed.gridx = 1;
		gbc_pnl_datumListed.gridy = 1;
		pnl_tab_Bestellungen.add(pnl_datumListed, gbc_pnl_datumListed);
		pnl_datumListed.setLayout(new BoxLayout(pnl_datumListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		pnl_datumListed.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		pnl_datumListed.add(lblNewLabel_5);
		
		JPanel pnl_mitarbeiterListed = new JPanel();
		GridBagConstraints gbc_pnl_mitarbeiterListed = new GridBagConstraints();
		gbc_pnl_mitarbeiterListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_mitarbeiterListed.insets = new Insets(0, 0, 5, 5);
		gbc_pnl_mitarbeiterListed.gridx = 2;
		gbc_pnl_mitarbeiterListed.gridy = 1;
		pnl_tab_Bestellungen.add(pnl_mitarbeiterListed, gbc_pnl_mitarbeiterListed);
		pnl_mitarbeiterListed.setLayout(new BoxLayout(pnl_mitarbeiterListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnl_mitarbeiterListed.add(lblNewLabel_6);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnl_mitarbeiterListed.add(lblNewLabel_1);
		
		JPanel pnl_bestellartListed = new JPanel();
		GridBagConstraints gbc_pnl_bestellartListed = new GridBagConstraints();
		gbc_pnl_bestellartListed.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnl_bestellartListed.insets = new Insets(0, 0, 5, 0);
		gbc_pnl_bestellartListed.gridx = 3;
		gbc_pnl_bestellartListed.gridy = 1;
		pnl_tab_Bestellungen.add(pnl_bestellartListed, gbc_pnl_bestellartListed);
		pnl_bestellartListed.setLayout(new BoxLayout(pnl_bestellartListed, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		pnl_bestellartListed.add(lblNewLabel_7);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		pnl_bestellartListed.add(lblNewLabel_3);
		
		JButton btn_bestellungHinzufuegen = new JButton("Bestellung hinzufügen");
		GridBagConstraints gbc_btn_bestellungHinzufuegen = new GridBagConstraints();
		gbc_btn_bestellungHinzufuegen.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btn_bestellungHinzufuegen.gridx = 3;
		gbc_btn_bestellungHinzufuegen.gridy = 2;
		pnl_tab_Bestellungen.add(btn_bestellungHinzufuegen, gbc_btn_bestellungHinzufuegen);
		
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
	}

}

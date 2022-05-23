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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class BestellungenUerbersichtGUI {

	private JFrame frmSupplyfly;
	private JTable table;
	private JTextField txt_lieferanntenName;
	private JTextField txt_lieferantennNummer;
	private JTextField txt_ansprechPartner;
	private JTextField txt_telefon;
	private JTextField txt_strasse;
	private JTextField txt_plz;
	private JTextField txt_ort;
	private JTable table_1;
	private JButton btn_hinzufuegen;
	private JTable table_2;

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
		//supplyfly.datenbankzugriff.DBAccess.getConnectionToDatabase();
		//supplyfly.datenbankzugriff.DBAccess.getAlleBestellung();
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
		frmSupplyfly.setBounds(100, 100, 633, 410);
		frmSupplyfly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel pnl_tab_Produkte = new JPanel();
		tabbedPane.addTab("Produkte", null, pnl_tab_Produkte, null);
		
		table_2 = new JTable();
		GroupLayout gl_pnl_tab_Produkte = new GroupLayout(pnl_tab_Produkte);
		gl_pnl_tab_Produkte.setHorizontalGroup(
			gl_pnl_tab_Produkte.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Produkte.createSequentialGroup()
					.addContainerGap()
					.addComponent(table_2, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE))
		);
		gl_pnl_tab_Produkte.setVerticalGroup(
			gl_pnl_tab_Produkte.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Produkte.createSequentialGroup()
					.addContainerGap()
					.addComponent(table_2, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
		);
		pnl_tab_Produkte.setLayout(gl_pnl_tab_Produkte);
		
		JPanel pnl_tab_Lieferanten = new JPanel();
		tabbedPane.addTab("Lieferanten", null, pnl_tab_Lieferanten, null);
		
		JLabel lbl_titelLieferantenHinzufuegen = new JLabel("Lieferanten hinzuf\u00fcgen");
		lbl_titelLieferantenHinzufuegen.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_lieferantenName = new JLabel("Lieferantenname:");
		
		JLabel lbl_lieferantenNummer = new JLabel("Lieferanntennummer:");
		
		JLabel lbl_ansprechpartner = new JLabel("Ansprechpartner:");
		
		JLabel lbl_telefon = new JLabel("Telefon:");
		
		JLabel lbl_adresse = new JLabel("Adresse:");
		
		txt_lieferanntenName = new JTextField();
		txt_lieferanntenName.setColumns(10);
		
		txt_lieferantennNummer = new JTextField();
		txt_lieferantennNummer.setColumns(10);
		
		txt_ansprechPartner = new JTextField();
		txt_ansprechPartner.setColumns(10);
		
		txt_telefon = new JTextField();
		txt_telefon.setColumns(10);
		
		txt_strasse = new JTextField();
		txt_strasse.setColumns(10);
		
		txt_plz = new JTextField();
		txt_plz.setColumns(10);
		
		txt_ort = new JTextField();
		txt_ort.setColumns(10);
		
		JLabel lbl_titelProdukte = new JLabel("Produkte");
		
		table_1 = new JTable();
		
		JButton btn_produkteHinzufuegen = new JButton("Produkte hinzuf\u00fcgen");
		btn_produkteHinzufuegen.addActionListener((e -> {
			LieferantenProdukteHinzufuegenGUI gui = new LieferantenProdukteHinzufuegenGUI();
			gui.loadLieferantenProdukteHinzufuegenGUI();
		}));
		
		btn_hinzufuegen = new JButton("Hinzuf\u00fcgen");
		GroupLayout gl_pnl_tab_Lieferanten = new GroupLayout(pnl_tab_Lieferanten);
		gl_pnl_tab_Lieferanten.setHorizontalGroup(
			gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_titelLieferantenHinzufuegen)
						.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
								.addComponent(lbl_adresse)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txt_strasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
								.addComponent(lbl_telefon)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txt_telefon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
								.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
									.addComponent(lbl_ansprechpartner)
									.addComponent(lbl_lieferantenNummer))
								.addGap(12)
								.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
									.addComponent(txt_lieferantennNummer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txt_ansprechPartner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(txt_lieferanntenName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addComponent(txt_plz, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txt_ort, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lbl_lieferantenName))
					.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_titelProdukte)
						.addComponent(btn_produkteHinzufuegen)
						.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.TRAILING)
							.addComponent(btn_hinzufuegen)
							.addComponent(table_1, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)))
					.addGap(22))
		);
		gl_pnl_tab_Lieferanten.setVerticalGroup(
			gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addContainerGap()
							.addComponent(lbl_titelProdukte)
							.addGap(18)
							.addComponent(table_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addComponent(lbl_titelLieferantenHinzufuegen)
							.addGap(17)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_lieferantenName)
								.addComponent(txt_lieferanntenName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(txt_lieferantennNummer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_lieferantenNummer))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_ansprechpartner)
								.addComponent(txt_ansprechPartner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_telefon)
								.addComponent(txt_telefon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_adresse)
								.addComponent(txt_strasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txt_plz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
						.addComponent(txt_ort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_produkteHinzufuegen))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btn_hinzufuegen))
		);
		pnl_tab_Lieferanten.setLayout(gl_pnl_tab_Lieferanten);
		GroupLayout groupLayout = new GroupLayout(frmSupplyfly.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(25, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		JPanel pnl_tab_Bestellung = new JPanel();
		tabbedPane.addTab("Bestellung", null, pnl_tab_Bestellung, null);
		
		table = new JTable();
		
		JButton btn_bestellungenHinzufuegen = new JButton("Betsellung hinzuf\u00fcgen");
		btn_bestellungenHinzufuegen.addActionListener(e -> {
			BestellungHinzufuegeGUI gui = new BestellungHinzufuegeGUI();
			gui.loadBestellungHinzufuegenGUI();
		});
	
		GroupLayout gl_pnl_tab_Bestellung = new GroupLayout(pnl_tab_Bestellung);
		gl_pnl_tab_Bestellung.setHorizontalGroup(
			gl_pnl_tab_Bestellung.createParallelGroup(Alignment.TRAILING)
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
				.addGroup(gl_pnl_tab_Bestellung.createSequentialGroup()
					.addContainerGap(411, Short.MAX_VALUE)
					.addComponent(btn_bestellungenHinzufuegen)
					.addContainerGap())
		);
		gl_pnl_tab_Bestellung.setVerticalGroup(
			gl_pnl_tab_Bestellung.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Bestellung.createSequentialGroup()
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btn_bestellungenHinzufuegen))
		);
		pnl_tab_Bestellung.setLayout(gl_pnl_tab_Bestellung);
		frmSupplyfly.getContentPane().setLayout(groupLayout);
	}
}

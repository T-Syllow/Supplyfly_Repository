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
import java.net.URL;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import supplyfly.datenbankzugriff.DBAccess;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.JScrollPane;

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
	private JButton btn_hinzufuegen;
	private JTextField searchFieldProdukte;
	DBAccess db = new DBAccess();
	private JTable table_2;
	private JTable table_1;

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
	 * @throws Exception 
	 */
	public BestellungenUerbersichtGUI(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize(){
		
		frmSupplyfly = new JFrame();
		frmSupplyfly.setTitle("SupplyFly");
		frmSupplyfly.setBounds(100, 100, 633, 410);
		frmSupplyfly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		ImageIcon logo = new ImageIcon("img/Logo SupplyFly2.png");
		frmSupplyfly.setIconImage(logo.getImage());
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel pnl_tab_Produkte = new JPanel();
		tabbedPane.addTab("Produkte", null, pnl_tab_Produkte, null);
		tabbedPane.setBackgroundAt(0, SystemColor.menu);
		pnl_tab_Produkte.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		pnl_tab_Produkte.add(panel, BorderLayout.NORTH);
		
		JButton btn_produktHinzufuegen = new JButton("Produkt hinzuf\u00FCgen");
		btn_produktHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProduktHinzufuegenGUI produktFenster = new ProduktHinzufuegenGUI();
				produktFenster.loadLieferantenProdukteHinzufuegenGUI();
			}
		});
		panel.add(btn_produktHinzufuegen);
		
		JLabel lbl_search = new JLabel("Suche:");
		lbl_search.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lbl_search);
		
		searchFieldProdukte = new JTextField();
		panel.add(searchFieldProdukte);
		searchFieldProdukte.setColumns(20);
		
		JScrollPane scrollPane = new JScrollPane();
		pnl_tab_Produkte.add(scrollPane, BorderLayout.CENTER);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ProduktID", "Produktname"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_2.getColumnModel().getColumn(0).setResizable(false);
		DefaultTableModel model = (DefaultTableModel) table_2.getModel();
		try {
			db.getAlleProdukte(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		table_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					JTable clickedRow = (JTable) me.getSource();
					int zeile = clickedRow.getSelectedRow();
					int produktID = (int) table_2.getValueAt(zeile, 0);
					
					ProduktBearbeitenGUI2 produktBearbeiten = new ProduktBearbeitenGUI2(produktID);
					
					produktBearbeiten.loadLieferantenProdukteHinzufuegenGUI(produktID);
					
					
				}
			}
		});
		scrollPane.setViewportView(table_2);
		
		JPanel pnl_tab_Lieferanten = new JPanel();
		tabbedPane.addTab("Lieferanten", null, pnl_tab_Lieferanten, null);
		
		JLabel lbl_titelLieferantenHinzufuegen = new JLabel("<HTML><U>Lieferanten hinzuf\u00fcgen</U></HTML>");
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
		
		JLabel lbl_titelProdukte = new JLabel("<HTML><U>Produkte:</U></HTML>");
		
		JButton btn_produkteHinzufuegen = new JButton("Produkte hinzuf\u00fcgen");
		btn_produkteHinzufuegen.addActionListener((e -> {
			LieferantenProdukteHinzufuegenGUI gui = new LieferantenProdukteHinzufuegenGUI();
			gui.loadLieferantenProdukteHinzufuegenGUI();
		}));
		
		btn_hinzufuegen = new JButton("Hinzuf\u00fcgen");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_pnl_tab_Lieferanten = new GroupLayout(pnl_tab_Lieferanten);
		gl_pnl_tab_Lieferanten.setHorizontalGroup(
			gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnl_tab_Lieferanten.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn_hinzufuegen)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_titelLieferantenHinzufuegen)
								.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
									.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_ansprechpartner)
										.addComponent(lbl_lieferantenNummer)
										.addComponent(lbl_telefon)
										.addComponent(lbl_adresse))
									.addGap(12)
									.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txt_telefon)
										.addComponent(txt_lieferanntenName, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
										.addComponent(txt_lieferantennNummer)
										.addComponent(txt_ansprechPartner)
										.addComponent(txt_strasse)
										.addComponent(txt_plz)
										.addComponent(txt_ort)))
								.addComponent(lbl_lieferantenName))
							.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btn_produkteHinzufuegen)
								.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
								.addComponent(lbl_titelProdukte, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(22))
		);
		gl_pnl_tab_Lieferanten.setVerticalGroup(
			gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addGap(23)
							.addComponent(lbl_titelProdukte)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE))
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
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_adresse)
								.addComponent(txt_strasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txt_plz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addComponent(txt_ort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(92))
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addComponent(btn_produkteHinzufuegen)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btn_hinzufuegen)
							.addGap(22))))
		);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Produktname", "Preis"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(125);
		scrollPane_1.setViewportView(table_1);
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
		
		JButton btn_bestellungenHinzufuegen = new JButton("Bestellung hinzuf\u00FCgen");
		btn_bestellungenHinzufuegen.addActionListener(e -> {
			BestellungHinzufuegeGUI gui = new BestellungHinzufuegeGUI();
			gui.loadBestellungHinzufuegenGUI();
		});
	
		GroupLayout gl_pnl_tab_Bestellung = new GroupLayout(pnl_tab_Bestellung);
		gl_pnl_tab_Bestellung.setHorizontalGroup(
			gl_pnl_tab_Bestellung.createParallelGroup(Alignment.TRAILING)
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
				.addGroup(gl_pnl_tab_Bestellung.createSequentialGroup()
					.addContainerGap(454, Short.MAX_VALUE)
					.addComponent(btn_bestellungenHinzufuegen)
					.addContainerGap())
		);
		gl_pnl_tab_Bestellung.setVerticalGroup(
			gl_pnl_tab_Bestellung.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Bestellung.createSequentialGroup()
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btn_bestellungenHinzufuegen)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnl_tab_Bestellung.setLayout(gl_pnl_tab_Bestellung);
		frmSupplyfly.getContentPane().setLayout(groupLayout);
	}
}

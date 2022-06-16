package supplyfly.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

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
import supplyfly.objects.Einkaeufer;
import supplyfly.objects.Lieferant;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class BestellungenUerbersichtGUI {

	private JFrame frmSupplyfly;
	private JTextField txt_lieferanntenName;
	private JTextField txt_lieferantennNummer;
	private JTextField txt_ansprechPartner;
	private JTextField txt_strasse;
	private JTextField txt_plz;
	private JTextField txt_ort;
	private JButton btn_hinzufuegen;
	DBAccess db = new DBAccess();
	private JTable table_2;
	private JTable table_bestellungen;
	Einkaeufer aktuellerNutzer;
	private JTextField txt_hausnummer;
	private JTable table;
	private JTextField txt_preisProdukteVonLieferanten;
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
	
	//Nach Login wird dieser Konstruktor aufgerufen, um den aktuellen Nutzer zu erkennen.
	public BestellungenUerbersichtGUI(String realUsername, Integer nutzerID, String nutzerRolle) throws Exception {
		aktuellerNutzer = new Einkaeufer(realUsername, nutzerID, nutzerRolle);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}
	
	public BestellungenUerbersichtGUI() throws Exception{
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
	private void initialize() throws Exception{
		
		frmSupplyfly = new JFrame();
		frmSupplyfly.setTitle("SupplyFly");
		frmSupplyfly.setExtendedState(JFrame.MAXIMIZED_BOTH);	//VOLLBILD Einstellung
//		frmSupplyfly.setBounds(100, 100, 1000, 500);
		frmSupplyfly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		ImageIcon logo = new ImageIcon("img/Logo SupplyFly2.png");
		frmSupplyfly.setIconImage(logo.getImage());
		frmSupplyfly.setVisible(true);
		
		
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
		
		//Das Produktportfolio mit dem besten Lieferanten und seinem Preis fuer das Produkt.
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ProduktID", "Produktname", "Preis", "Standardlieferant","LieferantenNr"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		DefaultTableModel model = (DefaultTableModel) table_2.getModel();
		
		JButton btn_produktHinzufuegen = new JButton("Produkt hinzuf�gen");
		
		
		JButton btn_refreshProduktportfolio = new JButton("Aktualisieren");
		
		panel.add(btn_refreshProduktportfolio);
		
	
		panel.add(btn_produktHinzufuegen);
		
		JScrollPane scrollPane = new JScrollPane();
		pnl_tab_Produkte.add(scrollPane, BorderLayout.CENTER);
		
		table_2.getColumnModel().getColumn(0).setResizable(false);
		try {
			DBAccess.getAlleProdukte(model);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		table_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					JTable clickedRow = (JTable) me.getSource();
					int zeile = clickedRow.getSelectedRow();
					int produktID = (int) table_2.getValueAt(zeile, 0);
//					ProduktBearbeitenGUI2 produktBearbeiten = new ProduktBearbeitenGUI2(produktID);
					
					model.fireTableDataChanged();
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
		
		JLabel lbl_adresse = new JLabel("Strasse/Hausnr.:");
		
		txt_lieferanntenName = new JTextField();
		txt_lieferanntenName.setColumns(10);
		
		txt_lieferantennNummer = new JTextField();
		txt_lieferantennNummer.setColumns(10);
		
		txt_ansprechPartner = new JTextField();
		txt_ansprechPartner.setColumns(10);
		
		txt_strasse = new JTextField();
		txt_strasse.setColumns(10);
		
		txt_plz = new JTextField();
		txt_plz.setColumns(10);
		
		txt_ort = new JTextField();
		txt_ort.setColumns(10);
		
		//F�gt Lieferanten hinzu.
		btn_hinzufuegen = new JButton("Hinzuf\u00fcgen");
		
		
		
		//(Philipp) Produkt l�schen
		JButton btn_Produktloeschen = new JButton("Produkt l�schen");
		btn_Produktloeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")) {
					try {
						Integer row = table_2.getSelectedRow();
						String produktID = String.valueOf(model.getValueAt(row, 0));
			
						//loescht die ausgewaehltes Produkt nicht, sondern setzt es auf nichtAnzeigen
						DBAccess.loescheProdukt(produktID);
						JOptionPane.showMessageDialog(frmSupplyfly, "Produkt aus dem aktuellen Katalog entfernt");
						refreshTable(model);
					} catch (ArrayIndexOutOfBoundsException aoe) {
						JOptionPane.showMessageDialog(frmSupplyfly, "Wählen Sie zuerst ein Produkt in der Tabelle aus!");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frmSupplyfly, "Wählen Sie zuerst ein Produkt in der Tabelle aus!");
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(frmSupplyfly, "*ZUGRIFF VERWEIGERT*\nSie sind nicht berechtigt Produkte zu l�schen.");
				}
			}
		});
		btn_Produktloeschen.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btn_Produktloeschen);
		
		JLabel lbl_plz = new JLabel("PLZ:");
		
		JLabel lbl_ort = new JLabel("Ort:");
		
		txt_hausnummer = new JTextField();
		txt_hausnummer.setColumns(10);
		
		JButton btn_lieferantenBearbeiten = new JButton("Lieferanten bearbeiten");
		btn_lieferantenBearbeiten.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				try {
					LieferantenBearbeiten2.loadLieferantenBearbeiten2(aktuellerNutzer);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btn_addProdukteZuLieferanten = new JButton("Produkt hinzuf\u00FCgen");
		
		txt_preisProdukteVonLieferanten = new JTextField();
		txt_preisProdukteVonLieferanten.setColumns(10);
		
		JLabel lbl_preis = new JLabel("Preis:");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_pnl_tab_Lieferanten = new GroupLayout(pnl_tab_Lieferanten);
		gl_pnl_tab_Lieferanten.setHorizontalGroup(
			gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_titelLieferantenHinzufuegen)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_ansprechpartner)
								.addComponent(lbl_lieferantenNummer)
								.addComponent(lbl_adresse)
								.addComponent(lbl_plz)
								.addComponent(lbl_ort))
							.addGap(12)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txt_lieferanntenName, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
								.addComponent(txt_lieferantennNummer)
								.addComponent(txt_ansprechPartner)
								.addComponent(txt_plz)
								.addComponent(txt_ort)
								.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
									.addComponent(txt_strasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txt_hausnummer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(lbl_lieferantenName)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addComponent(btn_lieferantenBearbeiten)
							.addGap(38)
							.addComponent(btn_hinzufuegen)))
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addGap(31)
							.addComponent(lbl_preis)
							.addGap(61)
							.addComponent(txt_preisProdukteVonLieferanten, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(btn_addProdukteZuLieferanten))
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)))
					.addGap(18))
		);
		gl_pnl_tab_Lieferanten.setVerticalGroup(
			gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
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
							.addGap(42)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_adresse)
								.addComponent(txt_strasse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(txt_hausnummer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.TRAILING)
								.addComponent(txt_plz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_plz))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(txt_ort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_ort))
							.addGap(54)
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
								.addComponent(btn_lieferantenBearbeiten)
								.addComponent(btn_hinzufuegen)))
						.addGroup(gl_pnl_tab_Lieferanten.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))))
					.addGap(40)
					.addGroup(gl_pnl_tab_Lieferanten.createParallelGroup(Alignment.BASELINE)
						.addComponent(txt_preisProdukteVonLieferanten, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbl_preis)
						.addComponent(btn_addProdukteZuLieferanten))
					.addGap(58))
		);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"LieferantenNr", "Lieferanten Name"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_2.setViewportView(table_1);
		DefaultTableModel m1 = (DefaultTableModel) table_1.getModel();
		DBAccess.getAlleLieferantenShort(m1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ProduktId", "Produktbezeichnung"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		scrollPane_1.setViewportView(table);
		pnl_tab_Lieferanten.setLayout(gl_pnl_tab_Lieferanten);
		DefaultTableModel m = (DefaultTableModel) table.getModel();
		DBAccess.getAlleProdukteLieferanten(m);
		
		btn_produktHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aktuellerNutzer.getNutzerRolle().equals("MitarbeiterBeschaffung") || aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")) {
				ProduktHinzufuegenGUI produktFenster = new ProduktHinzufuegenGUI();
				produktFenster.loadLieferantenProdukteHinzufuegenGUI();
				while(produktFenster.isFrameVisible()) {
					try {
						wait(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				refreshTable(model);
				DBAccess.refreshProdukteTableShort(m);
				} else {
					JOptionPane.showMessageDialog(frmSupplyfly, "*ZUGRIFF VERWEIGERT*\nSie sind nicht berechtigt neue Produkte hinzuzufuegen!");
				}
			}
		});
		
		btn_addProdukteZuLieferanten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						if(aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")||aktuellerNutzer.getNutzerRolle().equals("MitarbeiterBeschaffung")) {
							try {
								Integer row = table.getSelectedRow();
								Integer row2 = table_1.getSelectedRow();
								Integer produktID = Integer.valueOf(m.getValueAt(row, 0).toString());
								Integer lieferantenID = Integer.valueOf(m1.getValueAt(row2, 0).toString());
								Double preis = Double.parseDouble(txt_preisProdukteVonLieferanten.getText());
								DBAccess.lieferantenProdukteHinzufuegen(produktID, lieferantenID, preis);
								
								JOptionPane.showMessageDialog(btn_addProdukteZuLieferanten, "Erfolgreich hinzugef�gt!");
								
							} catch (ArrayIndexOutOfBoundsException aoe) {
								JOptionPane.showMessageDialog( null ,"Wählen Sie zuerst ein Produkt in der Tabelle aus!");
							} catch (Exception e1) {
								JOptionPane.showMessageDialog( null ,"Wählen Sie zuerst ein Produkt in der Tabelle aus!");
							}finally {
							}
						}else {
							JOptionPane.showMessageDialog(null, "*ZUGRIFF VERWEIGERT*\nSie sind nicht berechtigt Lieferanten hinzuzuf�gen.");
						}
					
				}});
		
		btn_hinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)throws NullPointerException{
				if(aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")||aktuellerNutzer.getNutzerRolle().equals("MitarbeiterBeschaffung")) {
					try {
						Lieferant l = new Lieferant(txt_lieferanntenName.getText(), txt_ansprechPartner.getText(), Integer.parseInt(txt_lieferantennNummer.getText()), 
						txt_strasse.getText(), Integer.parseInt(txt_hausnummer.getText()),Integer.parseInt(txt_plz.getText()),txt_ort.getText());
						DBAccess d = new DBAccess();
						try {
							d.insertLieferantInDatabase(l);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("Lieferant hinzugef�gt.");
					}catch(NumberFormatException ex) {
						System.out.println("Die Felder d�rfen nicht leer sein.");
						JOptionPane.showMessageDialog(frmSupplyfly, "Die Felder nicht leer lassen. Bei PLZ, Hausnummer und LieferantenNr bitte NUMMERN eingeben!");
					}
					finally {
						txt_lieferanntenName.setText("");
						txt_ansprechPartner.setText("");
						txt_lieferantennNummer.setText("");
						txt_strasse.setText("");
						txt_hausnummer.setText("");
						txt_plz.setText("");
						txt_ort.setText("");
						DBAccess.refreshLieferantenTableShort(m1);
						
				}
			}else {
				JOptionPane.showMessageDialog(frmSupplyfly, "*ZUGRIFF VERWEIGERT*\nSie sind nicht berechtigt Lieferanten hinzuzuf�gen.");
			}
		}});
		
		btn_refreshProduktportfolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable(model);
				DBAccess.refreshProdukteTableShort(m);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frmSupplyfly.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
					.addGap(4))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 382, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel pnl_tab_Bestellung = new JPanel();
		tabbedPane.addTab("Bestellung", null, pnl_tab_Bestellung, null);
		
		// Button erlaubt nur LeiterBeschaffung und Mitarbeiter aus Beschaffung bzw. Lager eine neue Bestellung aufzugeben.
		JButton btn_bestellungenHinzufuegen = new JButton("Bestellung hinzuf�gen");
		btn_bestellungenHinzufuegen.addActionListener(e -> {
			if(aktuellerNutzer.getNutzerRolle().equals("MitarbeiterBeschaffung") || aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")) {
			BestellungHinzufuegeGUI.loadBestellungHinzufuegenGUI(aktuellerNutzer);
			} else if (aktuellerNutzer.getNutzerRolle().equals("MitarbeiterLager")){
			BestellungHinzufuegeGUI.loadBestellungHinzufuegenGUI(aktuellerNutzer);
			} else {
				JOptionPane.showMessageDialog(frmSupplyfly, "*ZUGRIFF VERWEIGERT!*\nNur die Beschaffungsabteilung hat Zugriff auf die Bearbeitung von Bestellungen.");
			}
		});
		
		JScrollPane scrollPane_Bestellung = new JScrollPane();
		
		table_bestellungen = new JTable();
		table_bestellungen.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"BestellNr", "Bestellart", "Bestellwert", "Mitarbeiter", "Datum", "Status", "Produkt","Menge"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		DefaultTableModel model_table_Bestellungen = (DefaultTableModel) table_bestellungen.getModel();
		
		//Dieser Button erlaubt nur dem LeiterBeschaffung bestellungen zu stornieren.
		JButton btn_bestellungLoeschen = new JButton("Bestellung stornieren");
		btn_bestellungLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")) {
					try {
						Integer row = table_bestellungen.getSelectedRow();
						Integer bestellNr = Integer.valueOf(model_table_Bestellungen.getValueAt(row, 0).toString());
						DBAccess.loescheBestellungInDB(bestellNr);
						JOptionPane.showMessageDialog(btn_bestellungLoeschen, "Die Bestellung "+bestellNr+" wurde storniert!");
						refreshTableBestellungen(model_table_Bestellungen);
					} catch (ArrayIndexOutOfBoundsException aoe) {
						JOptionPane.showMessageDialog(frmSupplyfly, "Wählen Sie zuerst eine Bestellung in der Tabelle aus!");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frmSupplyfly, "Wählen Sie zuerst eine Bestellung in der Tabelle aus!");
					}
				} else {
					JOptionPane.showMessageDialog(frmSupplyfly, "*ZUGRIFF VERWEIGERT*\nSie sind nicht berechtigt Bestellungen zu loeschen.");
				}
			}
		});
		
		JButton btn_aktualisiereBestellungen = new JButton("Aktualisieren");
		btn_aktualisiereBestellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTableBestellungen(model_table_Bestellungen);
			}
		});
	
		GroupLayout gl_pnl_tab_Bestellung = new GroupLayout(pnl_tab_Bestellung);
		gl_pnl_tab_Bestellung.setHorizontalGroup(
			gl_pnl_tab_Bestellung.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane_Bestellung, GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
				.addGroup(gl_pnl_tab_Bestellung.createSequentialGroup()
					.addContainerGap(551, Short.MAX_VALUE)
					.addComponent(btn_aktualisiereBestellungen)
					.addGap(18)
					.addComponent(btn_bestellungLoeschen)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_bestellungenHinzufuegen)
					.addGap(14))
		);
		gl_pnl_tab_Bestellung.setVerticalGroup(
			gl_pnl_tab_Bestellung.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnl_tab_Bestellung.createSequentialGroup()
					.addComponent(scrollPane_Bestellung, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
					.addGap(26)
					.addGroup(gl_pnl_tab_Bestellung.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_bestellungenHinzufuegen)
						.addComponent(btn_bestellungLoeschen)
						.addComponent(btn_aktualisiereBestellungen))
					.addGap(16))
		);
		
		
		
		refreshTableBestellungen(model_table_Bestellungen);
		
		//Button erlaubt nur LeiterBeschaffung un MitarbeiterBeschaffung den Zugriff auf die Bestellungsbearbeitung.
		table_bestellungen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//prueft Rechte des eingeloggten Nutzers.
				if(e.getClickCount()==2) {
					if(aktuellerNutzer.getNutzerRolle().equals("MitarbeiterBeschaffung") || aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")) {
						JTable clickedRow = (JTable) e.getSource();
						int zeile = clickedRow.getSelectedRow();
						String bestellNr;
						String produktID;
						Integer bestellNrInt = (Integer) table_bestellungen.getValueAt(zeile, 0);
						Integer produktIDInt = Integer.valueOf(table_bestellungen.getValueAt(zeile, 6).toString());
						bestellNr = String.valueOf(bestellNrInt);
						produktID = String.valueOf(produktIDInt);
					
						BestellungBearbeitenGUI bestellungBearbeiten = new BestellungBearbeitenGUI(bestellNr,produktID,aktuellerNutzer);
						frmSupplyfly.setVisible(false);
						bestellungBearbeiten.loadBestellungBearbeitenGUI(bestellNr,produktID,aktuellerNutzer);
					
						model_table_Bestellungen.fireTableDataChanged();
					} else {
						JOptionPane.showMessageDialog(frmSupplyfly, "*ZUGRIFF VERWEIGERT!*\nNur die Beschaffungsabteilung hat Zugriff auf die Bearbeitung von Bestellungen.");
						
					}
				}
			}
		});
		scrollPane_Bestellung.setViewportView(table_bestellungen);
		pnl_tab_Bestellung.setLayout(gl_pnl_tab_Bestellung);
		frmSupplyfly.getContentPane().setLayout(groupLayout);
	}

	//fuegt die Produkte aus produkt in table neu ein.. (aktualisiert)..
	public void refreshTable(DefaultTableModel model) {
		
		model.setRowCount(0);
		
		try {
			DBAccess.getAlleProdukte(model);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}
	// fuegt die Bestellungen aus bestellung_produkt in table_bestellung neu ein.. (aktualisiert)..
	public void refreshTableBestellungen(DefaultTableModel model) {
		
		model.setRowCount(0);
		
		try {
			DBAccess.getAlleBestellung(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}



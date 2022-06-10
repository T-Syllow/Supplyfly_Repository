package supplyfly.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import supplyfly.datenbankzugriff.DBAccess;
import supplyfly.objects.Bestellung;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class BestellungHinzufuegeGUI {

	private JFrame frmNeueBestellung;
	private JTable table_produkteDerBestellung;
	private JTextField txt_name_kommentar;
	private JTextField tf_produktId;
	private JTextField tf_menge;

	/**
	 * Launch the application.
	 */
	public void loadBestellungHinzufuegenGUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BestellungHinzufuegeGUI window = new BestellungHinzufuegeGUI();
					window.frmNeueBestellung.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BestellungHinzufuegeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNeueBestellung = new JFrame();
		frmNeueBestellung.setTitle("Bestellung erstellen");
		frmNeueBestellung.setBounds(100, 100, 750, 410);
		frmNeueBestellung.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon logo = new ImageIcon("img/Logo SupplyFly2.png");
		frmNeueBestellung.setIconImage(logo.getImage());
		
		JPanel panel = new JPanel();
		frmNeueBestellung.getContentPane().add(panel, BorderLayout.CENTER);
		
		table_produkteDerBestellung = new JTable();
		table_produkteDerBestellung.setColumnSelectionAllowed(true);
		table_produkteDerBestellung.setCellSelectionEnabled(true);
		table_produkteDerBestellung.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Produkt", "Menge"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			DefaultTableModel model_table_bestellungErstellen = (DefaultTableModel) table_produkteDerBestellung.getModel();
		
			JButton btn_zurueck = new JButton("Zur\u00fcck");
			btn_zurueck.addActionListener(e -> {
				frmNeueBestellung.setVisible(false);
			});
			
			
			JLabel lbl_produktID = new JLabel("ProduktID");
			
			JLabel lbl_menge = new JLabel("Menge:");
			
			JLabel lbl_gesamtwert = new JLabel("Gesamtwert:");
			
			JLabel lbl_wertGesamtwert = new JLabel("New label");
			
			JLabel lbl_Bestellart_1 = new JLabel("Bestellart:");
			
			JLabel lbl_name_2 = new JLabel("Kommentar:");
			
			txt_name_kommentar = new JTextField();
			txt_name_kommentar.setColumns(10);
			
			JCheckBox cbox_bestellart = new JCheckBox("Eilbestellung");
			
			tf_produktId = new JTextField();
			tf_produktId.setColumns(10);
			
			tf_menge = new JTextField();
			tf_menge.setColumns(10);
			
			
			//ComboBox zur Auswahl der Lieferanten
			//ArrayList wird erstellt mit aktuellen Lieferanten
			ArrayList<String> lieferantenliste = DBAccess.getLieferanten();
			JComboBox comboBox_Lieferant = new JComboBox(lieferantenliste.toArray());

			
			
			JButton btn_produktHinzufuegen = new JButton("Produkt hinzufÃ¼gen");
			btn_produktHinzufuegen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//HIER MUSS EIN OBJEKT VON BESTELLUNG INSTANZIIERT WERDEN...
					//(Philipp) oder direkt in DB einfügen, ist mit SQL einfacher als mit Objekten
					
					//'ProduktID' und 'menge' werden dem Textfeld entnommen. Danach der JTable hinzugefuegt.
					String produktID = tf_produktId.getText();
					String menge = tf_menge.getText();
					model_table_bestellungErstellen.addRow(new Object[] {produktID,menge}); 
					
					//'bestellNr' brauchen wir  nicht. ATTRIBUT ist auf AUTOINKREMENT 
					
					//Variable soll Bestellwert fÃ¼r ein Produkt der Bestellung beinhalten. Er wird weitergegeben an das DefaultTableModel.
					String produktBestellwert = supplyfly.objects.Bestellung.calculateBestellwert(produktID, menge); 

					//'Produkt'(-Liste) wird als ArrayList<Integer> gespeichert, siehe Konstruktor von Bestellung.
					ArrayList<Integer> produktliste = new ArrayList<>();
					//Diese Schleife speichert jede ProduktID die in der JTable steht in die produktliste!
					for (int i = 0; i < model_table_bestellungErstellen.getRowCount(); i++) {
						produktliste.add((Integer)model_table_bestellungErstellen.getValueAt(i, 0));
					}

					//wir benoetigen fuer Bestellung einen ueberladenen Konstruktor --> OHNE bestellNr UND ZUSAETZLICH MIT lieferantenNr
					//(Philipp) Ausgeklammert, da ich den einfachen Weg ohne Objekt versuche
					//Bestellung neueBestellung = new Bestellung();
					
					//Diese Methode programmiert EMIN. :)
					//(Philipp) Ausgeklammert, da ich den einfachen Weg ohne Objekt versuche
					//DBAccess.speichereBestellungInDatabase(neueBestellung, model_table_bestellungErstellen);
					
					//loesche alle Eingabefelder im GUI..
					lbl_produktID.setText("");
					lbl_menge.setText("");
				}
			});
			
			
			//Bestätigen Button (legt eine Bestellung in "Bestellung" an und Je position die einzelnen Positionen in "bestellung-produkt"
			JButton btn_bestellungBestaetigen = new JButton("Best\u00e4tigen");
			btn_bestellungBestaetigen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//'bestellart' soll aus checkbox entnommen werden und in String variable gespeichert werden.
					String bestellart = "Reguläre Bestellung";
					if(cbox_bestellart.isSelected()) {
						bestellart = "Eilbestellung";
					}
					
					
					//Das Attribut 'mitarbeitername' soll aus dem Login entnommen werden. -> Wer ist eingeloggt?
					String mitarbeiterName = "MitarbeiterTest";
					
					
					//'Datum' soll automatisch uebertragen werden.
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
					String now = LocalDateTime.now().toString();
					String datum = now;
					
					
					//'Status' kann so bleiben!
					String status = "Bestellung aufgegeben";
					
					
					//'LieferantenNr' (fehlt noch im Konstruktur) (kommt aus der ComboBox)
					String gewaehlterLieferant = (String) comboBox_Lieferant.getSelectedItem();
					String [] geteilterLieferantInNummerUndName = gewaehlterLieferant.split(",");
					String lieferantenNr = geteilterLieferantInNummerUndName[0];
				
					
					// (Philipp) Bestellung direkt in der DB anlegen:
					Integer aktuelleBestellNr = DBAccess.getAktuelleBestellNr() + 1;
					DBAccess.legeBestellungInDBan(aktuelleBestellNr, bestellart, mitarbeiterName, datum, status, lieferantenNr);
					//DBAccess.legePositionenInDBan();
					
					
				}
			});
			
			
			
			JLabel lbl_Lieferant_1_1 = new JLabel("Lieferant:");
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(19)
						.addComponent(table_produkteDerBestellung, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_gesamtwert)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(lbl_produktID)
													.addComponent(lbl_Bestellart_1)
													.addGroup(gl_panel.createSequentialGroup()
														.addComponent(lbl_menge, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED))
													.addGroup(gl_panel.createSequentialGroup()
														.addComponent(lbl_name_2, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lbl_Lieferant_1_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)))
											.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_panel.createSequentialGroup()
													.addGap(69)
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(cbox_bestellart, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
														.addComponent(lbl_wertGesamtwert)
														.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(comboBox_Lieferant, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
													.addContainerGap())
												.addGroup(gl_panel.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addComponent(tf_menge, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
														.addComponent(tf_produktId, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
													.addGap(46))))))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btn_bestellungBestaetigen)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btn_zurueck)))
							.addGroup(gl_panel.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btn_produktHinzufuegen)
								.addContainerGap())))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(31)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_produktID)
									.addComponent(tf_produktId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_menge)
									.addComponent(tf_menge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btn_produktHinzufuegen)
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(comboBox_Lieferant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lbl_Lieferant_1_1))
								.addGap(28)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_Bestellart_1)
									.addComponent(cbox_bestellart))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_name_2)
									.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_gesamtwert)
									.addComponent(lbl_wertGesamtwert)))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(18)
								.addComponent(table_produkteDerBestellung, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)))
						.addGap(37)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_zurueck)
							.addComponent(btn_bestellungBestaetigen))
						.addContainerGap())
			);
			panel.setLayout(gl_panel);
		}
	}

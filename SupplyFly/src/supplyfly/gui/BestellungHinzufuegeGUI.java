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
import supplyfly.objects.Einkaeufer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ItemEvent;
import java.awt.Color;

public class BestellungHinzufuegeGUI {

	private JFrame frmNeueBestellung;
	private JTable table_produkteDerBestellung;
	private JTextField txt_name_kommentar;
	private JTextField tf_produktId;
	private JTextField tf_menge;
	private static Einkaeufer aktuellerNutzer;

	/**
	 * Launch the application.
	 */
	
	//(TOMMY) Neuer Konstruktor für das Rechtesystem. Hier wird der Parameter aktuellerNutzer nur fuer 'MitarbeiterLager' gebraucht,
	//(TOMMY) weil MitarbeiterLager nur Bestellungen bis zu einem bestimmten Preis aufgeben duerfen!
	public static void loadBestellungHinzufuegenGUI(Einkaeufer user) {
		aktuellerNutzer = user;
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
//		frmNeueBestellung.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmNeueBestellung.setBounds(100, 100, 1000, 500);
		frmNeueBestellung.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon logo = new ImageIcon("img/Logo SupplyFly2.png");
		frmNeueBestellung.setIconImage(logo.getImage());
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		frmNeueBestellung.getContentPane().add(panel, BorderLayout.CENTER);
		
		table_produkteDerBestellung = new JTable();
		table_produkteDerBestellung.setColumnSelectionAllowed(true);
		table_produkteDerBestellung.setCellSelectionEnabled(true);
		table_produkteDerBestellung.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Produkt", "Menge", "Wert", "g�ltig"
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
		
			JButton btn_zurueck = new JButton("Zur�ck");
			btn_zurueck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmNeueBestellung.setVisible(false);
					try {
						frmNeueBestellung.dispose();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			
			JLabel lbl_produktID = new JLabel("ProduktID");
			
			JLabel lbl_menge = new JLabel("Menge:");
			
			JLabel lbl_gesamtwert = new JLabel("Gesamtwert:");
			
			JLabel lbl_wertGesamtwert = new JLabel("0");
			
			JLabel lbl_Bestellart_1 = new JLabel("Bestellart:");
			
			JLabel lbl_name_2 = new JLabel("Kommentar:");
			
			txt_name_kommentar = new JTextField();
			txt_name_kommentar.setColumns(10);
			
			JCheckBox cbox_bestellart = new JCheckBox("Eilbestellung");
			
			tf_produktId = new JTextField();
			tf_produktId.setColumns(10);
			
			tf_menge = new JTextField();
			tf_menge.setColumns(10);
			
			
			
			//(Philipp) ComboBox zur Auswahl der Lieferanten
			//ArrayList wird erstellt mit aktuellen Lieferanten
			ArrayList<String> lieferantenliste = DBAccess.getLieferanten();
			JComboBox comboBox_Lieferant = new JComboBox(lieferantenliste.toArray());	
			
			//(Philipp) ben�tigt f�r btnHiinzuf�gen UND btnBest�tigen
			ArrayList<String> produktliste = new ArrayList<>();
			ArrayList<String> mengenliste = new ArrayList<>();

			
			//(Philipp) Produkt hinzuf�gen
			JButton btn_produktHinzufuegen = new JButton("Produkt hinzuf�gen");
			btn_produktHinzufuegen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					//'ProduktID' und 'menge' werden dem Textfeld entnommen. Danach der JTable hinzugefuegt. (Philipp) Ebenfalls wird angezeigt, ob das Produkt exisitiert / gel�scht bzw "G�ltig" ist
					String produktID = tf_produktId.getText();
					String menge = tf_menge.getText();
					model_table_bestellungErstellen.addRow(new Object[] {produktID,menge, "wird gepr�ft"}); 

					//alle aktuellen Produkte, die eingekauft werden k�nnen - Produkte, die der Lieferant NICHT liefert = Produkte, die bestellt werden k�nnen
					ArrayList<String> dbProduktliste = DBAccess.getProduktliste();	
					//Lieferant speichern
					String gewaehlterLieferant = (String) comboBox_Lieferant.getSelectedItem();
					String[] geteilterLieferantInNummerUndName = gewaehlterLieferant.split(",");
					String lieferantenNr = geteilterLieferantInNummerUndName[0];
					
					//Variable soll Bestellwert für ein Produkt der Bestellung beinhalten. Er wird weitergegeben an das DefaultTableModel.
					//produktBestellwert auskommentiert von Philipp, hat Exceptions verursacht und wird bisher nicht verwendet
					//Double produktBestellwert = supplyfly.objects.Bestellung.bestellwert(Double.valueOf(produktID),Integer.valueOf(menge),Integer.valueOf(lieferantenNr)); 
					
					//Nun die Produkte ausselektieren, die der gew�hlte Lieferant NICHT Liefert
					ArrayList<String> dbLieferantProduktliste = new ArrayList<>();			
					ResultSet rs = DBAccess.wasLiefertLieferantMitPreisUndMenge(lieferantenNr);
					try {
						while(rs.next()) {
							String produktIDlief = rs.getString("ProduktID");
							dbLieferantProduktliste.add(produktIDlief);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
					//Welche Produktliste sind g�ltige Produkte (DBProduktliste) UND von dem Lieferanten in seiner Produktliste, entferne die, die nicht vom Lieferanten geliefert werden
					ArrayList<String> lieferbareProdukte = new ArrayList<>();
					for (int i = 0; i < dbProduktliste.size(); i++) {
						if(dbLieferantProduktliste.contains(dbProduktliste.get(i))) {
							lieferbareProdukte.add(dbProduktliste.get(i));
						}else {
							dbProduktliste.remove(i);
						}
					}
					
					//Notwendig, da die Listen mit jedem mal Speichern neu gef�llt werden
					produktliste.clear();
					mengenliste.clear();
					//Diese Schleife speichert jede ProduktID die in der JTable steht in die produktliste!
					for (int i = 0; i < model_table_bestellungErstellen.getRowCount(); i++) {
						{
							if(model_table_bestellungErstellen.getValueAt(i, 0).equals("") || model_table_bestellungErstellen.getValueAt(i, 1).equals("")) {
								System.out.println("Zeile: " + (i+1) + " Felder d�rfen nicht leer sein!");
								JOptionPane.showMessageDialog(frmNeueBestellung, "Zeile: " + (i+1) + " Felder d�rfen nicht leer sein!");
								model_table_bestellungErstellen.setValueAt("ung�ltig", i, 2);
								model_table_bestellungErstellen.setValueAt("0", i, 3);
							}			
							else {
								if(dbProduktliste.contains(model_table_bestellungErstellen.getValueAt(i, 0))) {
									if(lieferbareProdukte.contains(model_table_bestellungErstellen.getValueAt(i, 0))) {
										produktliste.add((String)model_table_bestellungErstellen.getValueAt(i, 0));
										mengenliste.add((String) model_table_bestellungErstellen.getValueAt(i, 1));
										System.out.println("Zeile: " + (i+1) + " Produkt: " + model_table_bestellungErstellen.getValueAt(i, 0) + " Menge: " + model_table_bestellungErstellen.getValueAt(i, 1) + " als Position zwischengemerkt");
										model_table_bestellungErstellen.setValueAt("g�ltig", i, 2);
										
										//Noch den Preis der Postition hinzuf�gen:
										Integer produktPreis = DBAccess.getProduktpreisfuerLieferant(lieferantenNr, (String)model_table_bestellungErstellen.getValueAt(i, 0));
										Integer mengePos = Integer.parseInt((String) model_table_bestellungErstellen.getValueAt(i, 1));
										Integer posPreis = produktPreis * mengePos;
										String posPreisString = posPreis.toString();
										model_table_bestellungErstellen.setValueAt(posPreisString, i, 3);									
									}
									else {
										System.out.println("Produkt nicht bei diesem Lieferanten bestellbar! Machen Sie hierf�r eine neue Bestellung");
										JOptionPane.showMessageDialog(frmNeueBestellung, "Produkt nicht bei diesem Lieferanten bestellbar! Machen Sie hierf�r eine neue Bestellung");
										model_table_bestellungErstellen.setValueAt("ung�ltig", i, 2);
										model_table_bestellungErstellen.setValueAt("0", i, 3);
									}
								}
								else {
									System.out.println("Zeile: " + (i+1) + " Produkt exisitiert nicht, nicht vom Lieferanten lieferbar, hat keinen Standardlieferanten oder wurde gel�scht");
									JOptionPane.showMessageDialog(frmNeueBestellung, "Zeile: " + (i+1) + " Produkt exisitiert nicht, nicht vom Lieferanten lieferbar, hat keinen Standardlieferanten oder wurde gel�scht");
									model_table_bestellungErstellen.setValueAt("ung�ltig", i, 2);
									model_table_bestellungErstellen.setValueAt("0", i, 3);
								}
							}
						}
					}
					
//					// (Philipp) Gesamtwert aktualisieren (nur bei dem neusten Produkt)
					Integer bestellwert = 0;
					for (int i = 0; i < model_table_bestellungErstellen.getRowCount(); i++) {
						try {
							Integer produktPreis = DBAccess.getProduktpreisfuerLieferant(lieferantenNr, (String)model_table_bestellungErstellen.getValueAt(i, 0));
							Integer mengePos = Integer.parseInt((String) model_table_bestellungErstellen.getValueAt(i, 1));
							Integer posPreis = produktPreis * mengePos;
							bestellwert = bestellwert + posPreis;
							lbl_wertGesamtwert.setText(bestellwert.toString());
							
						}catch (Exception e1) {
							// extra leer, dient dazu, dass weitergez�hlt wird, auch wenn ein Feld mal leer ist
						}

					}

					//loesche alle Eingabefelder im GUI..
					lbl_produktID.setText("");
					lbl_menge.setText("");
				}
			});
			
			
			//(Philipp) Best�tigen Button (legt eine Bestellung in "Bestellung" an und Je position die einzelnen Positionen in "bestellung-produkt"
			JButton btn_bestellungBestaetigen = new JButton("Best�tigen");
			btn_bestellungBestaetigen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//'bestellart' soll aus checkbox entnommen werden und in String variable gespeichert werden.
					String bestellart = "Regul�re Bestellung";
					if(cbox_bestellart.isSelected()) {
						bestellart = "Eilbestellung";
					}
					
					
					//Das Attribut 'mitarbeitername' soll aus dem Login entnommen werden. -> Wer ist eingeloggt?
					String mitarbeiterName = aktuellerNutzer.getNutzername();
					
					
					//'Datum' soll automatisch uebertragen werden.
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
					String now = LocalDateTime.now().format(dtf).toString();
					String datum = now;
					
					
					
					//Speichert erneut die Lieferantennummer - Lieferant darf nicht ver�ndert werdern 
					String gewaehlterLieferant = (String) comboBox_Lieferant.getSelectedItem();
					String[] geteilterLieferantInNummerUndName = gewaehlterLieferant.split(",");
					String lieferantenNr = geteilterLieferantInNummerUndName[0];
					
					//'Status' kann so bleiben!
					String status = "Bestellung aufgegeben";
									
					
					//'Kommentar' f�r Bestellung aus textfeld
					String kommentar = txt_name_kommentar.getText();
					
					//Gesamtwert Bestellung
					String gesamtbestellwert = lbl_wertGesamtwert.getText();
					
					// (Philipp) Bestellung direkt in der DB anlegen:
					if(produktliste.isEmpty() || mengenliste.isEmpty()) {
						System.out.println("Kann keine leere Bestellung anlegen, f�gen Sie Produkte hinzu");
						JOptionPane.showMessageDialog(frmNeueBestellung, "Kann keine leere Bestellung anlegen, f�gen Sie Produkte hinzu");
					}else {
						Integer aktuelleBestellNr = DBAccess.getAktuelleBestellNr() + 1;
						DBAccess.legeBestellungInDBan(aktuelleBestellNr, bestellart, mitarbeiterName, datum, status, lieferantenNr, kommentar, gesamtbestellwert);
						//(Philipp) Positionen in DB anlegen - mit Bestellung verkn�pft
						for (int i = 0; i < produktliste.size(); i++) {
							DBAccess.legePositionenInDBan(aktuelleBestellNr, produktliste.get(i), mengenliste.get(i), (String)model_table_bestellungErstellen.getValueAt(i, 3));
						}	
						JOptionPane.showMessageDialog(frmNeueBestellung, "Bestellung erfolgreich angelegt");
						
						//(Philipp) CSV Datei f�r Lieferanten erstellen
						DBAccess.erstelleBestellungsTXT(aktuelleBestellNr.toString());
					}				
				}
			});
			

			
			JLabel lbl_Lieferant_1_1 = new JLabel("Lieferant (muss f�r alle Produkte gleich sein):");
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(19)
						.addComponent(table_produkteDerBestellung, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lbl_gesamtwert)
								.addGap(263)
								.addComponent(lbl_wertGesamtwert))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_produktHinzufuegen)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lbl_Bestellart_1)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lbl_produktID)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(tf_produktId, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lbl_menge, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(cbox_bestellart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(tf_menge, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
											.addComponent(txt_name_kommentar, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lbl_Lieferant_1_1, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(comboBox_Lieferant, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(lbl_name_2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(231, Short.MAX_VALUE))
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(808)
						.addComponent(btn_bestellungBestaetigen)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btn_zurueck, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
						.addGap(13))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(30)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_Lieferant_1_1)
									.addComponent(comboBox_Lieferant, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(44)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_produktID)
									.addComponent(tf_produktId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lbl_menge)
									.addComponent(tf_menge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btn_produktHinzufuegen)
								.addGap(31)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_Bestellart_1)
									.addComponent(cbox_bestellart))
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_name_2)
									.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(lbl_gesamtwert)
									.addComponent(lbl_wertGesamtwert)))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(18)
								.addComponent(table_produkteDerBestellung, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)))
						.addGap(121)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_zurueck)
							.addComponent(btn_bestellungBestaetigen))
						.addContainerGap())
			);
			panel.setLayout(gl_panel);
		}
	}

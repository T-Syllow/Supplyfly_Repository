package supplyfly.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import supplyfly.datenbankzugriff.DBAccess;
import supplyfly.objects.Bestellung;
import supplyfly.objects.Einkaeufer;

import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.awt.event.ActionEvent;

public class BestellungBearbeitenGUI {

	private JFrame frame;
	private JTable BestellungenUebersicht;
	private Einkaeufer aktuellerNutzer;

	/**
	 * Launch the application.
	 */
	public void loadBestellungBearbeitenGUI(String bestellNr, String produktID, Einkaeufer aktuellerNutzer) {
		this.aktuellerNutzer = aktuellerNutzer;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {	
					//er kriegt hier als Parameter den eigenen JFrame des Fensters mit! Nicht der JFrame von BestellungenUebersichtGUI.java
					BestellungBearbeitenGUI window = new BestellungBearbeitenGUI(bestellNr, produktID, aktuellerNutzer);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
	public BestellungBearbeitenGUI(String bestellNr, String produktID, Einkaeufer aktuellerNutzer) {
		initialize(bestellNr,produktID,aktuellerNutzer);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String bestellNr, String produktID, Einkaeufer aktuellerNutzer) {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		frame.setBounds(100, 100, 792, 527);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		BestellungenUebersicht = new JTable();
		BestellungenUebersicht.setColumnSelectionAllowed(true);
		BestellungenUebersicht.setCellSelectionEnabled(true);
		BestellungenUebersicht.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"BestellNr", "Bestellart", "Bestellwert", "Mitarbeiter", "Datum", "Status", "Produkte", "Menge", "LieferantenNr"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, true, true, true, true, true, true, true, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			DefaultTableModel model_table_Bestellungen = (DefaultTableModel) BestellungenUebersicht.getModel();
			try {
				// Schreibe passende Methode, die die Produkte der angeklickten Bestellung in die JTable lädt, in : 
				DBAccess.getSelectedBestellung(bestellNr, produktID, model_table_Bestellungen);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
		
		JLabel lbl_bestellnummer = new JLabel("Bestellnummer:");
		lbl_bestellnummer.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_bestelltvon = new JLabel("bestellt von:");
		lbl_bestelltvon.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_status = new JLabel("Status:");
		lbl_status.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_bestellwert = new JLabel("Bestellwert:");
		lbl_bestellwert.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_targetBestellnummer = new JLabel(DBAccess.getBestellInfo(bestellNr, "BestellNr"));
		
		JLabel lbl_targetBestelltvon = new JLabel(DBAccess.getBestellInfo(bestellNr, "Mitarbeiter"));
		
		JLabel lbl_targetStatus = new JLabel(DBAccess.getBestellInfo(bestellNr, "Status"));
		
		JLabel lbl_targetBestellwert = new JLabel(DBAccess.getBestellInfo(bestellNr, "Bestellwert"));
		
		JButton btn_zurueck = new JButton("Zurueck");
		btn_zurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				BestellungenUerbersichtGUI bestellungenUerbersichtGUI = new BestellungenUerbersichtGUI(aktuellerNutzer.getNutzername(), aktuellerNutzer.getNutzerId(), aktuellerNutzer.getNutzerRolle());
			}
		});
		
		JButton btn_bestaetigen1 = new JButton("Bestaetigen");
		btn_bestaetigen1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer bestellNrBestellung = 0;
				String bestellart = "";
				Double bestellwert = 0.0;
				String mitarbeiter = "";
				String datum = "";
				String status = "";
				Integer produkt = 404;
				Integer lieferantenNr = 9999;
				Double menge = 0.0;
				Integer rowCount = model_table_Bestellungen.getRowCount(); 
				;
				try {				
					for (int i = 0; i < rowCount; i++) {
					
						bestellNrBestellung = Integer.valueOf(bestellNr);
						bestellart = (String) model_table_Bestellungen.getValueAt(i, 1);
						mitarbeiter = model_table_Bestellungen.getValueAt(i, 3).toString();
						datum = model_table_Bestellungen.getValueAt(i, 4).toString();
						status = model_table_Bestellungen.getValueAt(i, 5).toString();
						produkt = Integer.valueOf(model_table_Bestellungen.getValueAt(i, 6).toString());
						menge = Double.valueOf(model_table_Bestellungen.getValueAt(i, 7).toString());
						lieferantenNr = Integer.valueOf(model_table_Bestellungen.getValueAt(i, 8).toString());
						bestellwert = Bestellung.bestellwert(menge, produkt, lieferantenNr);	
					
						//Lege neue Instanz von Bestellung an.
						supplyfly.objects.Bestellung bestellungEdit = new Bestellung(bestellNrBestellung, bestellart, bestellwert, mitarbeiter, datum, status, produkt, menge, lieferantenNr);
						DBAccess.ueberschreibeDatenInDatabase(bestellungEdit,model_table_Bestellungen);	
						System.out.println("Die Bestellung mit BestellNr. "+bestellNr+" und ProduktID. "+model_table_Bestellungen.getValueAt(i, 6).toString()+" wurde ueberschrieben: 'bestellung = neue '"+bestellungEdit.toString());
					}
					JOptionPane popUpFenster = new JOptionPane();
					popUpFenster.setVisible(true);
					popUpFenster.showMessageDialog(null, "Bestellung wurde erfolgreich aktualisiert");
				
//				Integer.parseInt(bestellNr), model_table_Bestellungen.getValueAt(1, 0).toString(),Double.valueOf((String)model_table_Bestellungen.getValueAt(2, 0)), 
//				model_table_Bestellungen.getValueAt(3, 0).toString(), model_table_Bestellungen.getValueAt(4, 0).toString(), model_table_Bestellungen.getValueAt(5, 0).toString(),DBAccess.getProduktIdsAsIntegerArrayList(model_table_Bestellungen.getValueAt(6, 0).toString())
				} catch(InputMismatchException ime) {
					System.out.println("FEHLER! BestellNr darf nur Zahlen beinhalten. Bitte in DB ändern.");
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lbl_bestelltvon, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
												.addGap(199)
												.addComponent(lbl_targetBestelltvon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lbl_bestellnummer, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
												.addGap(176)
												.addComponent(lbl_targetBestellnummer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lbl_status, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
												.addGap(237)
												.addComponent(lbl_targetStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addGap(366))
									.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
										.addComponent(lbl_bestellwert)
										.addGap(53)
										.addComponent(lbl_targetBestellwert)
										.addGap(73)))
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addComponent(btn_bestaetigen1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btn_zurueck, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)))
					.addGap(0))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_bestellnummer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lbl_targetBestellnummer))
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_bestelltvon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lbl_targetBestelltvon))
					.addGap(26)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_status)
						.addComponent(lbl_targetStatus))
					.addGap(128)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(lbl_bestellwert)
						.addComponent(lbl_targetBestellwert))
					.addGap(65)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_bestaetigen1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btn_zurueck, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(16))
		);
		scrollPane.setViewportView(BestellungenUebersicht);
		panel.setLayout(gl_panel);
		
	}

}

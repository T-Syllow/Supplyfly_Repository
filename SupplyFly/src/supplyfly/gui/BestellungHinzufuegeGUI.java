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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

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
		frmNeueBestellung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JButton btn_bestellungBestaetigen = new JButton("Best\u00e4tigen");
		
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
		
		JButton btn_produktHinzufuegen = new JButton("Produkt hinzufügen");
		btn_produktHinzufuegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//HIER MUSS EIN OBJEKT VON BESTELLUNG INSTANZIIERT WERDEN...
				
				
				//'ProduktID' und 'menge' werden dem Textfeld entnommen. Danach der JTable hinzugefuegt.
				String produktID = tf_produktId.getText();
				String menge = tf_menge.getText();
				model_table_bestellungErstellen.addRow(new Object[] {produktID,menge}); 
				
				//'bestellNr' brauchen wir hier nicht. ATTRIBUT ist in Datenbank auf AUTOINKREMENT gestellt. Es wird automatisch um 1 erhoeht.
				
				
				//'bestellart' soll aus checkbox entnommen werden und in String variable gespeichert werden.
				String bestellart = "";

				
				//Diese Variable soll nur den Bestellwert für ein Produkt der Bestellung beinhalten. Er wird weitergegeben an das DefaultTableModel.
				String produktBestellwert = supplyfly.objects.Bestellung.calculateBestellwert(produktID, menge); 
				
				
				//Das Attribut 'mitarbeitername' soll aus dem Login entnommen werden. -> Wer ist eingeloggt?
				String mitarbeiterName = "";
				
				
				//'Datum' soll ENTWEDER manuell vom Nutzer eingetragen werden koennen ODER automatisch uebertragen werden.
				String datum = "";
				
				
				//'Status' kann so bleiben!
				String status = "Bestellung versandt";
				
				
				//'Produkt'(-Liste) wird als ArrayList<Integer> gespeichert, siehe Konstruktor von Bestellung.
				ArrayList<Integer> produktliste = new ArrayList<>();
				//Diese Schleife speichert jede ProduktID die in der JTable steht in die produktliste!
				for (int i = 0; i < model_table_bestellungErstellen.getRowCount(); i++) {
					produktliste.add((Integer)model_table_bestellungErstellen.getValueAt(i, 0));
				}
				
				//'LieferantenNr' (fehlt noch im Konstruktur)
				String lieferantenNr = "";
				
				
				//wir benoetigen fuer Bestellung einen ueberladenen Konstruktor --> OHNE bestellNr UND ZUSAETZLICH MIT lieferantenNr
				Bestellung neueBestellung = new Bestellung();
				
				//Diese Methode programmiert EMIN. :)
				DBAccess.speichereBestellungInDatabase(neueBestellung, model_table_bestellungErstellen);
				
				//loesche alle Eingabefelder im GUI..
				lbl_produktID.setText("");
				lbl_menge.setText("");
			}
		});
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
											.addComponent(lbl_produktID)
											.addComponent(lbl_Bestellart_1)
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lbl_menge, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED))
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lbl_name_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_panel.createSequentialGroup()
												.addGap(69)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addComponent(cbox_bestellart, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
													.addComponent(lbl_wertGesamtwert)
													.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
							.addGap(68)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_Bestellart_1)
								.addComponent(cbox_bestellart))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_name_2)
								.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
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

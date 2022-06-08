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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import supplyfly.datenbankzugriff.DBAccess;

import javax.swing.JScrollPane;

public class BestellungBearbeitenGUI {

	private JFrame frame;
	private JTable BestellungenUebersicht;

	/**
	 * Launch the application.
	 */
	public void loadBestellungBearbeitenGUI(String bestellNr) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BestellungBearbeitenGUI window = new BestellungBearbeitenGUI(bestellNr);
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
	public BestellungBearbeitenGUI(String bestellNr) {
		initialize(bestellNr);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String bestellNr) {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lbl_bestellnummer = new JLabel("Bestellnummer:");
		
		JLabel lbl_bestelltvon = new JLabel("bestellt von:");
		
		JLabel lbl_status = new JLabel("Status:");
		
		JLabel lbl_bestellwert = new JLabel("Bestellwert:");
		
		JLabel lbl_targetBestellnummer = new JLabel("[EMPTY]");
		
		JLabel lbl_targetBestelltvon = new JLabel("[EMPTY]");
		
		JLabel lbl_targetStatus = new JLabel("[EMPTY]");
		
		JLabel lbl_targetBestellwert = new JLabel("[EMPTY]");
		
		JButton btn_bearbeiten = new JButton("Bearbeiten");
		
		JButton btn_zurueck = new JButton("Zurueck");
		
		JButton btn_bestaetigen = new JButton("Bestaetigen");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lbl_bestellnummer)
									.addComponent(lbl_bestelltvon)
									.addComponent(lbl_status)
									.addComponent(lbl_bestellwert))
								.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addComponent(lbl_targetBestellnummer)
									.addComponent(lbl_targetBestelltvon)
									.addComponent(lbl_targetStatus)
									.addComponent(lbl_targetBestellwert))
								.addContainerGap(50, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(btn_bearbeiten)
								.addContainerGap(477, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(btn_bestaetigen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_zurueck)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 356, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btn_bearbeiten))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(41)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_bestellnummer)
								.addComponent(lbl_targetBestellnummer))
							.addGap(33)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_bestelltvon)
								.addComponent(lbl_targetBestelltvon))
							.addGap(37)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_status)
								.addComponent(lbl_targetStatus))
							.addPreferredGap(ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_bestellwert)
								.addComponent(lbl_targetBestellwert))
							.addGap(87)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btn_zurueck)
								.addComponent(btn_bestaetigen))))
					.addContainerGap())
		);
		
		BestellungenUebersicht = new JTable();
		BestellungenUebersicht.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"BestellNr", "Bestellart", "Bestellwert", "Mitarbeiter", "Datum", "Status", "Produkte"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			DefaultTableModel model_table_Bestellungen = (DefaultTableModel) BestellungenUebersicht.getModel();
			try {
				// Schreibe passende Methode, die die Produkte der angeklickten Bestellung in die JTable lädt, in : 
				DBAccess.getSelectedBestellung(bestellNr, model_table_Bestellungen);
			} catch (Exception e1) {
				System.out.println(e1);
			}
		scrollPane.setViewportView(BestellungenUebersicht);
		panel.setLayout(gl_panel);
	}
}

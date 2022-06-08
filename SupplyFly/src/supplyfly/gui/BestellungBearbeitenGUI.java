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
import java.awt.Font;

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
		frame.setBounds(100, 100, 792, 527);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lbl_bestellnummer = new JLabel("Bestellnummer:");
		lbl_bestellnummer.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_bestelltvon = new JLabel("bestellt von:");
		lbl_bestelltvon.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_status = new JLabel("Status:");
		lbl_status.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_bestellwert = new JLabel("Bestellwert:");
		lbl_bestellwert.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		
		JLabel lbl_targetBestellnummer = new JLabel("[EMPTY]");
		
		JLabel lbl_targetBestelltvon = new JLabel("[EMPTY]");
		
		JLabel lbl_targetStatus = new JLabel("[EMPTY]");
		
		JLabel lbl_targetBestellwert = new JLabel("[EMPTY]");
		
		JButton btn_zurueck = new JButton("Zurueck");
		
		JButton btn_bestaetigen = new JButton("Bestaetigen");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lbl_bestelltvon)
											.addPreferredGap(ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
											.addComponent(lbl_targetBestelltvon))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lbl_bestellnummer)
											.addGap(176)
											.addComponent(lbl_targetBestellnummer))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lbl_status)
											.addPreferredGap(ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
											.addComponent(lbl_targetStatus)))
									.addGap(366))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lbl_bestellwert)
									.addGap(53)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lbl_targetBestellwert)
										.addComponent(btn_bestaetigen))
									.addGap(6)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_zurueck)
							.addGap(8)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_bestellnummer)
						.addComponent(lbl_targetBestellnummer))
					.addGap(28)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_bestelltvon)
						.addComponent(lbl_targetBestelltvon))
					.addGap(26)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_status)
						.addComponent(lbl_targetStatus))
					.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_bestellwert)
						.addComponent(lbl_targetBestellwert))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_zurueck)
						.addComponent(btn_bestaetigen))
					.addGap(16))
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
					true, true, true, true, true, true, true
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

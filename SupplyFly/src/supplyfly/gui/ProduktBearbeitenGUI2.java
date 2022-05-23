package supplyfly.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import supplyfly.datenbankzugriff.DBAccess;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProduktBearbeitenGUI2 {

	private JFrame frame;
	private JTextField txtField_proName;
	private JTextField txtField_artNummer;

	private JTextField txtField_mindMenge;
	private JTextField txtField_derzMenge;
	DBAccess db = new DBAccess();

	/**
	 * Launch the application.
	 */
	public void loadLieferantenProdukteHinzufuegenGUI (int produktID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProduktBearbeitenGUI2 window = new ProduktBearbeitenGUI2(produktID);
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
	public ProduktBearbeitenGUI2(int produktID) {
		initialize(produktID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int produktID) {

		frame = new JFrame();
		frame.setBounds(100, 100, 633, 410);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lbl_produktname = new JLabel("Produktname:");
		
		JLabel lbl_artikelnummer = new JLabel("Artikelnummer:");
		
		JLabel lbl_mindestmenge = new JLabel("Mindestmenge:");
		
		JLabel lbl_produktspezifikation = new JLabel("<HTML><U>Produktspezifikationen:</U></HTML>");
		lbl_produktspezifikation.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		txtField_proName = new JTextField(db.getProduktInfo(produktID, "Produktbezeichnung"));
	
		txtField_proName.setColumns(10);
		
		txtField_artNummer = new JTextField(String.valueOf(produktID));
		txtField_artNummer.setColumns(10);
		
		txtField_mindMenge = new JTextField(db.getProduktInfo(produktID, "Mindestbestand"));
		txtField_mindMenge.setColumns(10);
		
		JTextArea textArea = new JTextArea(db.getProduktInfo(produktID, "Produktspezifikation"));
		textArea.setLineWrap(true);
		
		JComboBox<Integer> comBox_lieferantenAuswahl = new JComboBox();
		comBox_lieferantenAuswahl.addItem(Integer.parseInt(db.getProduktInfo(produktID, "Standardlieferant")));
		JLabel lbl_standardlieferant = new JLabel("Standardlieferant:");
		
		JLabel lblNewLabel = new JLabel("Derzeitige Menge (optional):");
		
		txtField_derzMenge = new JTextField(db.getProduktInfo(produktID, "Menge"));
		txtField_derzMenge.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(18)
							.addComponent(txtField_derzMenge, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_produktname)
								.addComponent(lbl_artikelnummer)
								.addComponent(lbl_mindestmenge))
							.addGap(32)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtField_proName, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(txtField_artNummer, Alignment.LEADING)
									.addComponent(txtField_mindMenge, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addComponent(lbl_produktspezifikation))
					.addGap(38)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_standardlieferant)
						.addComponent(comBox_lieferantenAuswahl, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_produktname)
								.addComponent(txtField_proName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lbl_artikelnummer)
								.addComponent(txtField_artNummer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtField_mindMenge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_mindestmenge))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(txtField_derzMenge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(8)
							.addComponent(lbl_produktspezifikation)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lbl_standardlieferant)
							.addGap(10)
							.addComponent(comBox_lieferantenAuswahl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lbl_produktHinzufuegen = new JLabel("<HTML><U>Produkt bearbeiten</U></HTML>");
		lbl_produktHinzufuegen.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(lbl_produktHinzufuegen);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		JButton btn_zurueck_produkte = new JButton("Zur\u00fcck");
		panel_2.add(btn_zurueck_produkte);
		btn_zurueck_produkte.addActionListener(e -> {
			BestellungenUerbersichtGUI gui = new BestellungenUerbersichtGUI();
			frame.setVisible(false);
		});
		
		JButton btn_produkthinzufuegen = new JButton("Produkt speichern");
		panel_2.add(btn_produkthinzufuegen);
	}
	
}

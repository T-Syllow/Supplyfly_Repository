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
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class BestellungHinzufuegeGUI {

	private JFrame frmNeueBestellung;
	private JTable table_produkteDerBestellung;
	private JTextField txt_name_kommentar;

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
		frmNeueBestellung.setBounds(100, 100, 633, 410);
		frmNeueBestellung.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon logo = new ImageIcon("img/Logo SupplyFly2.png");
		frmNeueBestellung.setIconImage(logo.getImage());
		
		JPanel panel = new JPanel();
		frmNeueBestellung.getContentPane().add(panel, BorderLayout.CENTER);
		
		table_produkteDerBestellung = new JTable();
		
		JButton btn_bestellungbearbeiten = new JButton("Bearbeiten");
		
		JButton btn_zurueck = new JButton("Zur\u00fcck");
		btn_zurueck.addActionListener(e -> {
			frmNeueBestellung.setVisible(false);
		});
		
		JButton btn_bestellungBestaetigen = new JButton("Best\u00e4tigen");
		
		JLabel lbl_bestellnummer = new JLabel("Bestellnummer:");
		
		JLabel lbl_bestelltVon = new JLabel("Bestellt von:");
		
		JLabel lbl_status = new JLabel("Status:");
		
		JLabel lbl_gesamtwert = new JLabel("Gesamtwert:");
		
		JLabel lbl_wertGesamtwert = new JLabel("New label");
		
		JLabel lbl_wertBestellnummer = new JLabel("New label");
		
		JLabel lbl_wertBestelltVon = new JLabel("New label");
		
		JLabel lbl_wertStatus = new JLabel("New label");
		
		JButton btn_produktHinzufuegen = new JButton("Produkt hinzufügen");
		
		JLabel lbl_Bestellart_1 = new JLabel("Bestellart:");
		
		JLabel lbl_name_2 = new JLabel("Kommentar:");
		
		txt_name_kommentar = new JTextField();
		txt_name_kommentar.setColumns(10);
		
		JCheckBox cbox_bestellart = new JCheckBox("Eilbestellung");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(467, Short.MAX_VALUE)
					.addComponent(btn_bestellungBestaetigen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_zurueck))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_bestellungbearbeiten)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btn_produktHinzufuegen))
						.addComponent(table_produkteDerBestellung, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lbl_gesamtwert)
							.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
							.addComponent(lbl_wertGesamtwert)
							.addGap(63))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_bestellnummer)
								.addComponent(lbl_status)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lbl_Bestellart_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_bestelltVon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_name_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addGap(70)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lbl_wertStatus)
								.addComponent(lbl_wertBestelltVon)
								.addComponent(lbl_wertBestellnummer)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addComponent(cbox_bestellart, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
									.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(28, Short.MAX_VALUE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_bestellnummer)
								.addComponent(lbl_wertBestellnummer))
							.addGap(33)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_bestelltVon)
								.addComponent(lbl_wertBestelltVon))
							.addGap(36)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_status)
								.addComponent(lbl_wertStatus))
							.addGap(36)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_Bestellart_1)
								.addComponent(cbox_bestellart))
							.addGap(36)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_name_2)
								.addComponent(txt_name_kommentar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lbl_gesamtwert)
								.addComponent(lbl_wertGesamtwert)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addComponent(table_produkteDerBestellung, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(37)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btn_zurueck)
								.addComponent(btn_bestellungBestaetigen)))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btn_bestellungbearbeiten)
								.addComponent(btn_produktHinzufuegen))))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}
}

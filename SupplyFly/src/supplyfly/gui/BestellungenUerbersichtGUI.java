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

public class BestellungenUerbersichtGUI {

	private JFrame frmSupplyfly;

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
//		supplyfly.datenbankzugriff.DBAccess.getConnectionToDatabase();
//		supplyfly.datenbankzugriff.DBAccess.getAlleBestellung();
	}

	/**
	 * Create the application.
	 */
	public BestellungenUerbersichtGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSupplyfly = new JFrame();
		frmSupplyfly.setTitle("SupplyFly");
		frmSupplyfly.setBounds(100, 100, 450, 300);
		frmSupplyfly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmSupplyfly.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frmSupplyfly.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel pnl_Bestellungen = new JPanel();
		tabbedPane.addTab("Bestellungen", null, pnl_Bestellungen, null);
		GridBagLayout gbl_pnl_Bestellungen = new GridBagLayout();
		gbl_pnl_Bestellungen.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_pnl_Bestellungen.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnl_Bestellungen.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnl_Bestellungen.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnl_Bestellungen.setLayout(gbl_pnl_Bestellungen);
		
		JLabel lbl_bestellNr_titel = new JLabel("BestellNr.");
		GridBagConstraints gbc_lbl_bestellNr_titel = new GridBagConstraints();
		gbc_lbl_bestellNr_titel.weightx = 1.0;
		gbc_lbl_bestellNr_titel.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_bestellNr_titel.gridx = 0;
		gbc_lbl_bestellNr_titel.gridy = 0;
		pnl_Bestellungen.add(lbl_bestellNr_titel, gbc_lbl_bestellNr_titel);
		
		JLabel lbl_datum_titel = new JLabel("Datum");
		GridBagConstraints gbc_lbl_datum_titel = new GridBagConstraints();
		gbc_lbl_datum_titel.weightx = 1.0;
		gbc_lbl_datum_titel.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_datum_titel.gridx = 1;
		gbc_lbl_datum_titel.gridy = 0;
		pnl_Bestellungen.add(lbl_datum_titel, gbc_lbl_datum_titel);
		
		JLabel lbl_mitarbeiter_titel = new JLabel("Mitarbeiter");
		GridBagConstraints gbc_lbl_mitarbeiter_titel = new GridBagConstraints();
		gbc_lbl_mitarbeiter_titel.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_mitarbeiter_titel.weightx = 1.0;
		gbc_lbl_mitarbeiter_titel.gridx = 2;
		gbc_lbl_mitarbeiter_titel.gridy = 0;
		pnl_Bestellungen.add(lbl_mitarbeiter_titel, gbc_lbl_mitarbeiter_titel);
		
		JLabel lbl_bestellArt_titel = new JLabel("Bestellart");
		GridBagConstraints gbc_lbl_bestellArt_titel = new GridBagConstraints();
		gbc_lbl_bestellArt_titel.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_bestellArt_titel.weightx = 1.0;
		gbc_lbl_bestellArt_titel.gridx = 3;
		gbc_lbl_bestellArt_titel.gridy = 0;
		pnl_Bestellungen.add(lbl_bestellArt_titel, gbc_lbl_bestellArt_titel);
		
		JLabel lbl_BestellNr_1 = new JLabel("1");
		lbl_BestellNr_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer zahl = Integer.parseInt(lbl_BestellNr_1.getText());
				Integer neueZahl = zahl + 1;
				lbl_BestellNr_1.setText(neueZahl.toString());
			}
		});
		GridBagConstraints gbc_lbl_BestellNr_1 = new GridBagConstraints();
		gbc_lbl_BestellNr_1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_BestellNr_1.gridx = 0;
		gbc_lbl_BestellNr_1.gridy = 1;
		pnl_Bestellungen.add(lbl_BestellNr_1, gbc_lbl_BestellNr_1);
		
		JLabel lbl_datum_1 = new JLabel("1");
		GridBagConstraints gbc_lbl_datum_1 = new GridBagConstraints();
		gbc_lbl_datum_1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_datum_1.gridx = 1;
		gbc_lbl_datum_1.gridy = 1;
		pnl_Bestellungen.add(lbl_datum_1, gbc_lbl_datum_1);
		
		JLabel lbl_mitarbeiter_1 = new JLabel("1");
		GridBagConstraints gbc_lbl_mitarbeiter_1 = new GridBagConstraints();
		gbc_lbl_mitarbeiter_1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_mitarbeiter_1.gridx = 2;
		gbc_lbl_mitarbeiter_1.gridy = 1;
		pnl_Bestellungen.add(lbl_mitarbeiter_1, gbc_lbl_mitarbeiter_1);
		
		JLabel lbl_bestellart_1 = new JLabel("1");
		GridBagConstraints gbc_lbl_bestellart_1 = new GridBagConstraints();
		gbc_lbl_bestellart_1.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_bestellart_1.gridx = 3;
		gbc_lbl_bestellart_1.gridy = 1;
		pnl_Bestellungen.add(lbl_bestellart_1, gbc_lbl_bestellart_1);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 2;
		pnl_Bestellungen.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 2;
		pnl_Bestellungen.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 2;
		gbc_lblNewLabel_6.gridy = 2;
		pnl_Bestellungen.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_7.gridx = 3;
		gbc_lblNewLabel_7.gridy = 2;
		pnl_Bestellungen.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 3;
		pnl_Bestellungen.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 1;
		gbc_lblNewLabel_9.gridy = 3;
		pnl_Bestellungen.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 2;
		gbc_lblNewLabel_10.gridy = 3;
		pnl_Bestellungen.add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_11.gridx = 3;
		gbc_lblNewLabel_11.gridy = 3;
		pnl_Bestellungen.add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		JButton btn_addBestellung = new JButton("Bestellung hinzufügen");
		GridBagConstraints gbc_btn_addBestellung = new GridBagConstraints();
		gbc_btn_addBestellung.weightx = 0.2;
		gbc_btn_addBestellung.gridx = 3;
		gbc_btn_addBestellung.gridy = 7;
		pnl_Bestellungen.add(btn_addBestellung, gbc_btn_addBestellung);
		
		JPanel pnl_Produkte = new JPanel();
		tabbedPane.addTab("Produkte", null, pnl_Produkte, null);
		GridBagLayout gbl_pnl_Produkte = new GridBagLayout();
		gbl_pnl_Produkte.columnWidths = new int[]{0};
		gbl_pnl_Produkte.rowHeights = new int[]{0};
		gbl_pnl_Produkte.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pnl_Produkte.rowWeights = new double[]{Double.MIN_VALUE};
		pnl_Produkte.setLayout(gbl_pnl_Produkte);
		
		JPanel pnl_Lieferanten = new JPanel();
		tabbedPane.addTab("Lieferanten", null, pnl_Lieferanten, null);
		GridBagLayout gbl_pnl_Lieferanten = new GridBagLayout();
		gbl_pnl_Lieferanten.columnWidths = new int[]{0};
		gbl_pnl_Lieferanten.rowHeights = new int[]{0};
		gbl_pnl_Lieferanten.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pnl_Lieferanten.rowWeights = new double[]{Double.MIN_VALUE};
		pnl_Lieferanten.setLayout(gbl_pnl_Lieferanten);
	}

}

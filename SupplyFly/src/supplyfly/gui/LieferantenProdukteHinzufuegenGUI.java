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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class LieferantenProdukteHinzufuegenGUI {

	private JFrame frmLieferantenprodukteVerwalten;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public void loadLieferantenProdukteHinzufuegenGUI () {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LieferantenProdukteHinzufuegenGUI window = new LieferantenProdukteHinzufuegenGUI();
					window.frmLieferantenprodukteVerwalten.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LieferantenProdukteHinzufuegenGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLieferantenprodukteVerwalten = new JFrame();
		frmLieferantenprodukteVerwalten.setTitle("Lieferantenprodukte verwalten");
		frmLieferantenprodukteVerwalten.setBounds(100, 100, 633, 410);
		frmLieferantenprodukteVerwalten.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frmLieferantenprodukteVerwalten.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btn_Speichern = new JButton("Speichern");
		panel_1.add(btn_Speichern);
		
		JScrollPane scrollPane = new JScrollPane();
		frmLieferantenprodukteVerwalten.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Liefert", "ProduktID", "Produktname"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(0).setMaxWidth(45);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(125);
		scrollPane.setViewportView(table);
	}
}

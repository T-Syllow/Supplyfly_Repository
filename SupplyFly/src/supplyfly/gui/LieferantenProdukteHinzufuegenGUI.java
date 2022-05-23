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
		frmLieferantenprodukteVerwalten.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmLieferantenprodukteVerwalten.getContentPane().add(panel, BorderLayout.NORTH);
		
		table = new JTable();
		
		JButton btn_speichern = new JButton("Speichern");
		
		JButton btn_abwaehlen = new JButton("Abw\u00e4hlen");
		
		JButton btn_auswaehlen = new JButton("Ausw\u00e4hlen");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(293, Short.MAX_VALUE)
					.addComponent(btn_auswaehlen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_abwaehlen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_speichern)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 619, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(8, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_speichern)
						.addComponent(btn_abwaehlen)
						.addComponent(btn_auswaehlen))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}
}

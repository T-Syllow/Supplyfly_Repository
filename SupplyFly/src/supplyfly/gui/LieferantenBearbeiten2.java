package supplyfly.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import supplyfly.datenbankzugriff.DBAccess;
import supplyfly.objects.Einkaeufer;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class LieferantenBearbeiten2 {

	private JFrame frame;
	private JTable table;
	private static Einkaeufer aktuellerNutzer;
	/**
	 * Launch the application.
	 */
	public static void loadLieferantenBearbeiten2(Einkaeufer e) {
		aktuellerNutzer = e;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LieferantenBearbeiten2 window = new LieferantenBearbeiten2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	
	public LieferantenBearbeiten2() throws Exception {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btn_lieferantLoeschen = new JButton("Lieferant l\u00F6schen");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
						.addComponent(btn_lieferantLoeschen))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_lieferantLoeschen)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"LieferantNr", "Name", "Ansprechpartner", "Strasse", "Hausnummer", "PLZ", "Ort"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, Integer.class, Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(groupLayout);
		DBAccess d = new DBAccess();
		DefaultTableModel m = (DefaultTableModel) table.getModel();
		d.getAlleLieferanten(m);
		
		btn_lieferantLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aktuellerNutzer.getNutzerRolle().equals("LeiterBeschaffung")||aktuellerNutzer.getNutzerRolle().equals("MitarbeiterBeschaffung")) {
					try {
						Integer row = table.getSelectedRow();
						Integer lieferantenNr = Integer.valueOf(m.getValueAt(row, 0).toString());
						DBAccess.deleteLieferantInDatabase(lieferantenNr);
						JOptionPane.showMessageDialog(btn_lieferantLoeschen, "Der Lieferant "+ lieferantenNr +" wurde gelöscht!");
						
					} catch (ArrayIndexOutOfBoundsException aoe) {
						JOptionPane.showMessageDialog( null ,"WÃ¤hlen Sie zuerst einen Lieferanten in der Tabelle aus!");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog( null ,"WÃ¤hlen Sie zuerst einen Lieferanten in der Tabelle aus!");
					}finally {
						DBAccess d = new DBAccess();
						d.refreshLieferantenTable(m);
					}
				}else {
					JOptionPane.showMessageDialog(frame, "*ZUGRIFF VERWEIGERT*\nSie sind nicht berechtigt Lieferanten hinzuzufügen.");
				}
			
		}});
		
	}

}

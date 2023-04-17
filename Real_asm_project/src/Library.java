import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Library extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtAuthor;
	private JTextField txtYearPublish;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnUpload;
	private JTextField txtSearch;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	ArrayList<Book> L = new ArrayList();
	private JLabel Logo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Library frame = new Library();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Library() {

		URL urlIconNotepad = Library.class.getResource("logo.png");
		Image img = Toolkit.getDefaultToolkit().createImage(urlIconNotepad);
		this.setIconImage(img);

		setTitle("Library Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(297, 80, 453, 366);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(19, 100, 61, 16);
		contentPane.add(lblNewLabel);

		JLabel lblBookName = new JLabel("Book Name");
		lblBookName.setBounds(19, 128, 78, 16);
		contentPane.add(lblBookName);

		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(19, 156, 61, 16);
		contentPane.add(lblAuthor);

		JLabel lblNewLabel_3 = new JLabel("Year Publish");
		lblNewLabel_3.setBounds(19, 184, 78, 16);
		contentPane.add(lblNewLabel_3);

		txtID = new JTextField();
		txtID.setBounds(97, 95, 188, 26);
		contentPane.add(txtID);
		txtID.setColumns(10);
//		txtID.setEditable(true);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(97, 123, 188, 26);
		contentPane.add(txtName);

		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(97, 151, 188, 26);
		contentPane.add(txtAuthor);

		txtYearPublish = new JTextField();
		txtYearPublish.setColumns(10);
		txtYearPublish.setBounds(97, 179, 188, 26);
		contentPane.add(txtYearPublish);

		table = new JTable();
		table.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

		scrollPane.setViewportView(table);
		DefaultTableModel model = new DefaultTableModel();
		Object[] column = { "ID", "Name", "Author", "Year Published" };
		Object[] row = new Object[4];
		model.setColumnIdentifiers(column);
		table.setModel(model);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				// get data from table and put it into TEXT AREA
				try {
					txtID.setText((String) model.getValueAt(i, 0));

					txtName.setText(model.getValueAt(i, 1).toString());
					txtAuthor.setText(model.getValueAt(i, 2).toString());
					txtYearPublish.setText((String) model.getValueAt(i, 3));
				} catch (Exception e99) {
					JOptionPane.showMessageDialog(null, "Please hit UPLOAD button to RELOAD table");
				}
			}
		});

		btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("save.png"))));
		btnSave.setBounds(150, 327, 117, 43);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Ghi du lieu
					FileOutputStream fout = new FileOutputStream("BookList.txt");
					for (Book b : L)
						b.GhiFile(fout);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Data is already save");
				}
			}
		});
		contentPane.add(btnSave);

		btnUpload = new JButton("Upload");
		btnUpload.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("upload.png"))));
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// load du lieu tu file len table
				L = new ArrayList<Book>(); // xoa du lieu cu~ trong L
				try {
					model.setRowCount(0);
					FileInputStream fin = new FileInputStream("BookList.txt");
					InputStreamReader isr = new InputStreamReader(fin);
					BufferedReader br = new BufferedReader(isr);
					String line;

					while ((line = br.readLine()) != null) {
						String[] words = line.split(", ");
						row[0] = words[0];
						row[1] = words[1];
						row[2] = words[2];
						row[3] = words[3];
						model.addRow(row);
						int id = Integer.parseInt(words[0]);
						int year = Integer.parseInt(words[3]);
						Book b = new Book(id, words[1], words[2], year);
						L.add(b);
					}

				} catch (Exception e2) {
//					System.out.println("Error" + e2);
					JOptionPane.showMessageDialog(null, "Upload successful","Notify",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnUpload.setBounds(646, 51, 104, 28);
		contentPane.add(btnUpload);

		// ADD function
		JButton btnAdd = new JButton("Add");
		btnAdd.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("add.png"))));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String n, a;
				int iz, y;
				try {
					n = txtName.getText();
					a = txtAuthor.getText();
					iz = Integer.parseInt(txtID.getText());
					y = Integer.parseInt(txtYearPublish.getText());

					Book B = new Book(iz, n, a, y);
					try {
						for (Book b : L)
							if (B.getId() == b.getId()) {
								JOptionPane.showMessageDialog(null, "Duplicated ID", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
					} catch (Exception e88) {
						throw e88;
					}
					L.add(B); // Luu B vao List
					row[0] = iz;
					row[1] = n;
					row[2] = a;
					row[3] = y;
					model.addRow(row);// Dua du lien xuong bang

					txtName.setText("");
					txtID.setText("");
					txtAuthor.setText("");
					txtYearPublish.setText("");
					JOptionPane.showMessageDialog(null, "Add Successfully!");
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, "Please fill complete information");
				}

			}
		});
		btnAdd.setBounds(6, 272, 117, 43);
		contentPane.add(btnAdd);

		// UPDATE function
		btnUpdate = new JButton("Update");
		btnUpdate.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("update.png"))));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int i = table.getSelectedRow();
				String n, a;
				int iz, y;
				try {
					model.setValueAt(txtID, i, 0);
					model.setValueAt(txtName, i, 1);
					model.setValueAt(txtAuthor, i, 2);
					model.setValueAt(txtYearPublish, i, 3);

					n = txtName.getText();
					a = txtAuthor.getText();
					iz = Integer.parseInt(txtID.getText());
					y = Integer.parseInt(txtYearPublish.getText());

					Book B = new Book(iz, n, a, y);
					try {
						for (Book b : L)
							if (B.getId() == b.getId()) {
								JOptionPane.showMessageDialog(null, "Duplicated ID", "Error",
										JOptionPane.ERROR_MESSAGE);
								return;
							}

					} catch (Exception e88) {
						throw e88;
					}

					row[0] = iz;
					row[1] = n;
					row[2] = a;
					row[3] = y;
					// Dua du lien xuong bang
					model.removeRow(i);
					model.insertRow(i, row);

					L.set(i, B); // Luu B vao List
					try {
						// Ghi du lieu
						FileOutputStream fout = new FileOutputStream("BookList.txt");
						for (Book b : L)
							b.GhiFile(fout);
					} catch (Exception e15) {
						System.out.println("Error" + e15);
					}
					txtName.setText("");
					txtID.setText("");
					txtAuthor.setText("");
					txtYearPublish.setText("");
					JOptionPane.showMessageDialog(null, "Update Successfully!");
				} catch (Exception e4) {
					System.out.println("Error" + e4);
				}
			}

		});

		btnUpdate.setBounds(150, 272, 117, 43);
		contentPane.add(btnUpdate);

		// DELETE function
		btnDelete = new JButton("Delete");
		btnDelete.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("delete.png"))));
		btnDelete.setBounds(6, 327, 117, 43);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int z = table.getSelectedRow();
					System.out.print(z);
					if (z >= 0) {
						int result = JOptionPane.showConfirmDialog(null, "You want to delete this record?",
								"Caution!!!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							L.remove(z);
							model.removeRow(z);
							try {
								// Ghi du lieu
								FileOutputStream fout = new FileOutputStream("BookList.txt");
								for (Book b : L)
									b.GhiFile(fout);
							} catch (Exception e15) {
								System.out.println("Error" + e15);
							}
							JOptionPane.showMessageDialog(null, "Remove successfully!");
						} else if (result == JOptionPane.NO_OPTION) {
						}

					} else {
						JOptionPane.showMessageDialog(null, "Select a row First");
					}
				} catch (Exception e100) {
					System.out.println("Error" + e100);
				}

			}

		});

		// SEARCH function
		txtSearch = new JTextField();
		txtSearch.setText("Enter book ID");
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtSearch.getText().equals("Enter book ID")) {
					txtSearch.setText("");
//					txtSearch.setForeground(new Color(153, 153, 153));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtSearch.getText().equals("")) {
					txtSearch.setText("Enter book ID");
//					txtSearch.setForeground(new Color(153, 153, 153));
				}
			}
		});
		txtSearch.setToolTipText("");
		txtSearch.setForeground(new Color(0, 0, 0));
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					DefaultTableModel tab = (DefaultTableModel) table.getModel();
					String search = txtSearch.getText().toLowerCase();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);

					table.setRowSorter(tr);
					tr.setRowFilter(RowFilter.regexFilter(search));
				} catch (Exception e5) {
					throw e5;

				}
			}
		});
		txtSearch.setBounds(307, 13, 320, 26);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Search");
		lblNewLabel_1.setBounds(629, 13, 78, 26);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setIcon(
				new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("search1.png"))));

		Logo = new JLabel("jaVALORANT Library");
		Logo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Library.class.getResource("logo.png"))));
		Logo.setBounds(19, 6, 218, 65);
		contentPane.add(Logo);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtName.setText("");
//				txtID.setEditable(true);
				txtID.setText("");
				txtAuthor.setText("");
				txtYearPublish.setText("");
			}
		});
		btnClear.setBounds(6, 212, 117, 29);
		contentPane.add(btnClear);

	}
}

package view;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import model.Settings;
import model.Sound;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JLabel;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JPasswordField;

public class ApplicationView extends JFrame {
	private static Controller ctrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ctrl = new Controller();
					ApplicationView frame = new ApplicationView();

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
	public ApplicationView() {
		setTitle("Max!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 550);
		getContentPane().setLayout(null);

		global = new JPanel();
		global.setBackground(Color.WHITE);
		global.setBounds(0, 0, 900, 550);
		getContentPane().add(global);
		global.setLayout(null);

		sideBar = new JPanel();
		sideBar.setBackground(Color.DARK_GRAY);
		sideBar.setBounds(0, 0, 180, 550);
		global.add(sideBar);
		sideBar.setLayout(null);

		panelHome = new JPanel();
		panelHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NavigateTo(homeScreen);
			}
		});
		panelHome.setBackground(Color.DARK_GRAY);
		panelHome.setBounds(0, 70, 180, 50);
		sideBar.add(panelHome);
		panelHome.setLayout(null);

		homeIcon = new JLabel("");
		homeIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/home.png")));
		homeIcon.setBounds(25, 10, 30, 30);
		panelHome.add(homeIcon);

		barHome = new JLabel("");
		barHome.setOpaque(true);
		barHome.setBackground(Color.RED);
		barHome.setBounds(0, 0, 10, 50);
		panelHome.add(barHome);

		homeLabel = new JLabel("Accueil");
		homeLabel.setForeground(SystemColor.menu);
		homeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		homeLabel.setBounds(70, 20, 45, 13);
		panelHome.add(homeLabel);

		panelSounds = new JPanel();
		panelSounds.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NavigateTo(soundsScreen);
			}
		});
		panelSounds.setLayout(null);
		panelSounds.setBackground(Color.DARK_GRAY);
		panelSounds.setBounds(0, 120, 180, 50);
		sideBar.add(panelSounds);

		soundsIcon = new JLabel("");
		soundsIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/009.png")));
		soundsIcon.setBounds(25, 10, 30, 30);
		panelSounds.add(soundsIcon);

		barSounds = new JLabel("");
		barSounds.setBackground(Color.RED);
		barSounds.setBounds(0, 0, 10, 50);
		panelSounds.add(barSounds);

		soundsLabel = new JLabel("Mes sons");
		soundsLabel.setForeground(SystemColor.menu);
		soundsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		soundsLabel.setBounds(70, 20, 75, 13);
		panelSounds.add(soundsLabel);

		panelHistory = new JPanel();
		panelHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NavigateTo(historyScreen);
			}
		});
		panelHistory.setLayout(null);
		panelHistory.setBackground(Color.DARK_GRAY);
		panelHistory.setBounds(0, 170, 180, 50);
		sideBar.add(panelHistory);

		historyIcon = new JLabel("");
		historyIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/009.png")));
		historyIcon.setBounds(25, 10, 30, 30);
		panelHistory.add(historyIcon);

		barHistory = new JLabel("");
		barHistory.setBackground(Color.RED);
		barHistory.setBounds(0, 0, 10, 50);
		panelHistory.add(barHistory);

		historyLabel = new JLabel("Historique");
		historyLabel.setForeground(SystemColor.menu);
		historyLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		historyLabel.setBounds(70, 20, 75, 13);
		panelHistory.add(historyLabel);

		panelSettings = new JPanel();
		panelSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				NavigateTo(settingsScreen);
				//Load settings
				Settings settings = ctrl.GetSettings();
				if (settings != null) {
					userEmail.setText(settings.getEmailAddress());
					//Set threshold
					notificationCheckBox.setSelected(settings.isNotifyByEmail());
					emailKey.setText(settings.getEmailKey());
				}
				
			}
		});
		panelSettings.setLayout(null);
		panelSettings.setBackground(Color.DARK_GRAY);
		panelSettings.setBounds(0, 430, 180, 50);
		sideBar.add(panelSettings);

		settingsIcon = new JLabel("");
		settingsIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/008.png")));
		settingsIcon.setBounds(25, 10, 30, 30);
		panelSettings.add(settingsIcon);

		barSettings = new JLabel("");
		barSettings.setBackground(Color.RED);
		barSettings.setBounds(0, 0, 10, 50);
		panelSettings.add(barSettings);

		settingsLabel = new JLabel("Param\u00E8tres");
		settingsLabel.setForeground(SystemColor.menu);
		settingsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		settingsLabel.setBounds(70, 20, 75, 13);
		panelSettings.add(settingsLabel);

		homeScreen = new JPanel();
		homeScreen.setBackground(Color.WHITE);
		homeScreen.setBounds(180, 120, 720, 430);
		global.add(homeScreen);
		homeScreen.setLayout(null);

		playIcon = new JLabel("");
		playIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] options = { "Oui", "Non" };
				int n = JOptionPane.showOptionDialog(global,
						"L'enregistrement est sur le point de démarrer. Voulez-vous continuer ?", "Lancer Max!",
						JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options, options[1]);
				if (n == JOptionPane.NO_OPTION) {
					return;
				}

				playIcon.setVisible(false);
				stopIcon.setVisible(true);
				playStatus.setText("En cours...");

				homeScreen.repaint();
				ctrl.Record();
			}
		});
		playIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/play-button-small.png")));
		playIcon.setBounds(310, 250, 100, 100);
		homeScreen.add(playIcon);

		playStatus = new JLabel("Commencer");
		playStatus.setFont(new Font("Tahoma", Font.BOLD, 30));
		playStatus.setBounds(270, 138, 181, 30);
		homeScreen.add(playStatus);

		stopIcon = new JLabel("");
		stopIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] options = { "Oui", "Non" };
				int n = JOptionPane.showOptionDialog(global, "Voulez-vous arrêter l'enregistrement ?",
						"Arrêter enregistrement", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options,
						options[1]);
				if (n == JOptionPane.NO_OPTION) {
					return;
				}

				ctrl.StopRecord();

				stopIcon.setVisible(false);
				playIcon.setVisible(true);
				playStatus.setText("Commencer");

				homeScreen.repaint();
				
				NavigateTo(historyScreen);

			}
		});
		stopIcon.setVisible(false);
		stopIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/stop-button-small.png")));
		stopIcon.setBounds(310, 250, 100, 100);
		homeScreen.add(stopIcon);

		header = new JPanel();
		header.setBounds(180, 0, 720, 120);
		global.add(header);
		header.setLayout(null);

		exitLabel = new JLabel("");
		exitLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] options = { "Oui", "Non" };
				int n = JOptionPane.showOptionDialog(global, "Etes-vous sûr de vouloir quitter l'application ?",
						"Fermer l'application", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, null, options,
						options[1]);
				if (n == JOptionPane.YES_OPTION) {
					System.exit(0);
				}

			}
		});
		header.setLayout(null);
		exitLabel.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/closing.png")));
		exitLabel.setBounds(680, 45, 30, 30);
		header.add(exitLabel);

		soundsScreen = new JPanel();
		soundsScreen.setBackground(Color.WHITE);
		soundsScreen.setVisible(false);
		soundsScreen.setBounds(180, 120, 720, 430);
		global.add(soundsScreen);
		soundsScreen.setLayout(null);

		JLabel mySoundsLabel = new JLabel("Mes sons:");
		mySoundsLabel.setBounds(0, 0, 98, 25);
		mySoundsLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		soundsScreen.add(mySoundsLabel);

		JButton addSoundButton = new JButton("Ajouter un son");
		addSoundButton.setBounds(438, 10, 195, 33);
		addSoundButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser j = new JFileChooser();
				j.setFileFilter(new FileNameExtensionFilter("*.wav", "wav"));
				int r = j.showSaveDialog(null);
				// if the user selects a file
				if (r == JFileChooser.APPROVE_OPTION) {
					// set the label to the path of the selected file
					String filePath = j.getSelectedFile().getAbsolutePath();
					try {
						ctrl.AddSound(filePath);
						JOptionPane.showMessageDialog(global, "Le fichier " + filePath + " a bien été ajouté.");
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(global, e1.getMessage());
					}

					reloadSoundTable();
				}
			}
		});
		addSoundButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		soundsScreen.add(addSoundButton);

		soundsTable = new JTable();
		reloadSoundTable();

		scrollPane = new JScrollPane(soundsTable);
		scrollPane.setBounds(0, 80, 720, 350);
		scrollPane.setBackground(Color.WHITE);
		soundsScreen.add(scrollPane, BorderLayout.CENTER);

		historyScreen = new JPanel();
		historyScreen.setBackground(Color.WHITE);
		historyScreen.setVisible(false);
		historyScreen.setBounds(180, 120, 720, 430);
		global.add(historyScreen);
		historyScreen.setLayout(null);

		settingsScreen = new JPanel();
		settingsScreen.setBackground(Color.WHITE);
		settingsScreen.setVisible(false);
		settingsScreen.setBounds(180, 120, 720, 430);
		global.add(settingsScreen);
		settingsScreen.setLayout(null);
		
		settingsTitle = new JLabel("Param\u00E8tres");
		settingsTitle.setBounds(0, 0, 186, 39);
		settingsTitle.setFont(new Font("Tahoma", Font.BOLD, 32));
		settingsScreen.add(settingsTitle);
		
		emailLabel = new JLabel("Email :");
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		emailLabel.setBounds(0, 70, 82, 29);
		settingsScreen.add(emailLabel);
		
		userEmail = new JTextField();
		userEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userEmail.setBackground(Color.WHITE);
		userEmail.setBounds(200, 70, 350, 28);
		settingsScreen.add(userEmail);
		userEmail.setColumns(10);
		
		JLabel notifyLabel = new JLabel("Notification :");
		notifyLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		notifyLabel.setBounds(0, 120, 157, 29);
		settingsScreen.add(notifyLabel);
		
		notificationCheckBox = new JCheckBox("");
		notificationCheckBox.setBackground(Color.WHITE);
		notificationCheckBox.setBounds(200, 125, 93, 21);
		settingsScreen.add(notificationCheckBox);
		
		keyLabel = new JLabel("Cl\u00E9 :");
		keyLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		keyLabel.setBounds(0, 170, 54, 29);
		settingsScreen.add(keyLabel);
		
		emailKey = new JPasswordField();
		emailKey.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailKey.setBounds(150, 170, 400, 28);
		settingsScreen.add(emailKey);
		
		saveSettingsButton = new JButton("Enregistrer ces param\u00E8tres");
		saveSettingsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Don't forget to set the threshold (0 for now)
				Settings settings = new Settings(userEmail.getText(), 0, notificationCheckBox.isSelected(), String.valueOf(emailKey.getPassword()));
				
				ctrl.SaveSettings(settings);
				JOptionPane.showMessageDialog(global, "Les paramètres ont bien été sauvegardés.");
			}
		});
		saveSettingsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saveSettingsButton.setBounds(200, 304, 350, 39);
		settingsScreen.add(saveSettingsButton);
	}

	public void updateBar(JLabel label) {
		barHome.setOpaque(false);
		barSounds.setOpaque(false);
		barHistory.setOpaque(false);
		barSettings.setOpaque(false);
		label.setOpaque(true);
		sideBar.repaint();

	}

	public void updateScreen(JPanel selectedPanel) {
		homeScreen.setVisible(false);
		soundsScreen.setVisible(false);
		historyScreen.setVisible(false);
		settingsScreen.setVisible(false);

		selectedPanel.setVisible(true);
		global.repaint();
	}

	public void reloadSoundTable() {

		List<Sound> sounds = ctrl.GetSounds();

		Object[][] rows = new Object[sounds.size()][4];

		System.out.println(sounds.size());

		for (int i = 0; i < sounds.size(); i++) {
			rows[i][0] = sounds.get(i).getDate();
			rows[i][1] = sounds.get(i).getFilePath();
			rows[i][2] = "ecouter";
			rows[i][3] = "supprimer";

		}
		DefaultTableModel model = new DefaultTableModel(rows, new Object[] { "Date", "Nom", "Ecouter", "Supprimer" }) {
			@Override
			public void setValueAt(Object aValue, int row, int column) {
				if (column == 3 && (aValue instanceof Boolean)) {
					boolean pushed = (boolean) aValue;
					if (pushed) {
						// delete sound
						ctrl.DeleteSound(this.getValueAt(row, 1).toString());
						reloadSoundTable();
					}
				}
				if (column == 2 && (aValue instanceof Boolean)) {
					boolean pushed = (boolean) aValue;
					if (pushed) {
						try {
							ctrl.PlaySound(this.getValueAt(row, 1).toString());
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		soundsTable.setModel(model);
		soundsTable.getColumn("Ecouter").setCellRenderer(new ButtonRenderer());
		soundsTable.getColumn("Ecouter").setCellEditor(new SoundButton(new JCheckBox()));

		soundsTable.getColumn("Supprimer").setCellRenderer(new ButtonRenderer());
		soundsTable.getColumn("Supprimer").setCellEditor(new SoundButton(new JCheckBox()));

		soundsTable.setPreferredScrollableViewportSize(soundsTable.getPreferredSize());

		soundsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		soundsTable.getColumnModel().getColumn(3).setPreferredWidth(100);

		soundsScreen.repaint();

	}
	
	public void NavigateTo(JPanel screen) {
		JLabel[] bar = {barHome, barSounds, barHistory, barSettings};
		JPanel[] screens = {homeScreen, soundsScreen, historyScreen, settingsScreen};
		for (int i=0; i<screens.length; i++) {
			if (screen.equals(screens[i]))
			{
				updateBar(bar[i]);
				updateScreen(screens[i]);
			}
		}
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class SoundButton extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;

		public SoundButton(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {

			return isPushed;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}
	}

	/**
	 * Declare variables
	 */

	private JPanel global;
	private JPanel sideBar;
	private JPanel panelHome;
	private JLabel homeIcon;
	private JLabel barHome;
	private JLabel homeLabel;
	private JPanel panelSounds;
	private JLabel soundsIcon;
	private JLabel barSounds;
	private JLabel soundsLabel;
	private JPanel panelHistory;
	private JLabel historyIcon;
	private JLabel barHistory;
	private JLabel historyLabel;
	private JLabel settingsIcon;
	private JLabel barSettings;
	private JPanel panelSettings;
	private JLabel settingsLabel;
	private JPanel homeScreen;
	private JLabel playIcon;
	private JLabel playStatus;
	private JLabel stopIcon;
	private JPanel header;
	private JLabel exitLabel;
	private JPanel soundsScreen;
	private JPanel historyScreen;
	private JPanel settingsScreen;
	private JTable soundsTable;
	private JScrollPane scrollPane;
	private JLabel settingsTitle;
	private JLabel emailLabel;
	private JTextField userEmail;
	private JCheckBox notificationCheckBox;
	private JLabel keyLabel;
	private JPasswordField emailKey;
	private JButton saveSettingsButton;
}

package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.Color;
import javax.swing.JLabel;
import javax.mail.internet.AddressException;
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

import controller.Controller;
import model.Settings;
import model.Sound;
import model.Record;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSlider;

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
		setBounds(100, 100, 916, 550);
		getContentPane().setLayout(null);

		global = new JPanel();
		global.setBackground(Color.WHITE);
		global.setBounds(0, 0, 900, 550);
		getContentPane().add(global);
		global.setLayout(null);

		historyScreen = new JPanel();
		historyScreen.setBackground(Color.WHITE);
		historyScreen.setVisible(false);
		historyScreen.setBounds(180, 0, 720, 510);
		global.add(historyScreen);
		historyScreen.setLayout(null);

		historyHeader = new JPanel();
		historyHeader.setBounds(0, 0, 720, 120);
		historyScreen.add(historyHeader);
		GridBagLayout gbl_historyHeader = new GridBagLayout();
		gbl_historyHeader.columnWidths = new int[] { 337, 46, 0 };
		gbl_historyHeader.rowHeights = new int[] { 13, 0 };
		gbl_historyHeader.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_historyHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		historyHeader.setLayout(gbl_historyHeader);

		historyHeaderLabel = new JLabel("Historique");
		historyHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		GridBagConstraints gbc_historyHeaderLabel = new GridBagConstraints();
		gbc_historyHeaderLabel.gridwidth = 0;
		gbc_historyHeaderLabel.gridheight = 0;
		gbc_historyHeaderLabel.gridx = 0;
		gbc_historyHeaderLabel.gridy = 0;
		historyHeader.add(historyHeaderLabel, gbc_historyHeaderLabel);

		historyContent = new JPanel();
		historyContent.setBounds(0, 120, 720, 390);
		historyScreen.add(historyContent);
		historyContent.setLayout(null);

		historyScrollPanel = new JScrollPane();
		historyScrollPanel.setBounds(0, 0, 720, 390);
		historyContent.add(historyScrollPanel);

		recordsTable = new JTable();
		recordsTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Date de d\u00E9but", "Date de fin", "Nombre d'aboiements" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		recordsTable.getColumnModel().getColumn(0).setResizable(false);
		recordsTable.getColumnModel().getColumn(1).setResizable(false);
		recordsTable.getColumnModel().getColumn(2).setResizable(false);
		recordsTable.getColumnModel().getColumn(2).setPreferredWidth(123);
		historyScrollPanel.setViewportView(recordsTable);

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
				// Load settings
				Settings settings = ctrl.GetSettings();
				if (settings != null) {
					userEmail.setText(settings.getEmailAddress());
					// Set threshold
					notificationCheckBox.setSelected(settings.isNotifyByEmail());
					emailKey.setText(settings.getEmailKey());
					amplitudeInput.setValue((int) (settings.getAmplitudeThreshold() * 100));
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
		homeScreen.setBounds(180, 0, 720, 510);
		global.add(homeScreen);
		homeScreen.setLayout(null);

		homeHeader = new JPanel();
		homeHeader.setBounds(0, 0, 720, 120);
		homeScreen.add(homeHeader);
		GridBagLayout gbl_homeHeader = new GridBagLayout();
		gbl_homeHeader.columnWidths = new int[] { 720, 0 };
		gbl_homeHeader.rowHeights = new int[] { 120, 0 };
		gbl_homeHeader.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_homeHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		homeHeader.setLayout(gbl_homeHeader);

		playStatus = new JLabel("Commencer");
		playStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
		playStatus.setFont(new Font("Tahoma", Font.BOLD, 36));
		GridBagConstraints gbc_playStatus = new GridBagConstraints();
		gbc_playStatus.gridx = 0;
		gbc_playStatus.gridy = 0;
		homeHeader.add(playStatus, gbc_playStatus);

		homeContent = new JPanel();
		homeContent.setBackground(Color.WHITE);
		homeContent.setBounds(0, 120, 720, 390);
		homeScreen.add(homeContent);
		GridBagLayout gbl_homeContent = new GridBagLayout();
		gbl_homeContent.columnWidths = new int[] { 310, 100, 0 };
		gbl_homeContent.rowHeights = new int[] { 100, 0, 0, 0 };
		gbl_homeContent.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_homeContent.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		homeContent.setLayout(gbl_homeContent);

		playIcon = new JLabel("");
		playIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playIcon.setVisible(false);
				stopIcon.setVisible(true);
				playStatus.setText("En cours...");

				homeScreen.repaint();
				ctrl.Record();
			}
		});
		playIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/play-button-small.png")));
		GridBagConstraints gbc_playIcon = new GridBagConstraints();
		gbc_playIcon.gridwidth = 0;
		gbc_playIcon.gridheight = 0;
		gbc_playIcon.gridx = 0;
		gbc_playIcon.gridy = 0;
		homeContent.add(playIcon, gbc_playIcon);

		stopIcon = new JLabel("");
		stopIcon.setIcon(new ImageIcon(ApplicationView.class.getResource("/view/img/stop-button-small.png")));
		stopIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ctrl.StopRecord();

				stopIcon.setVisible(false);
				playIcon.setVisible(true);
				playStatus.setText("Commencer");

				homeScreen.repaint();
				
				reloadRecordsTable();

				NavigateTo(historyScreen);
			}
		});
		GridBagConstraints gbc_stopIcon = new GridBagConstraints();
		gbc_stopIcon.gridwidth = 0;
		gbc_stopIcon.gridheight = 0;
		gbc_stopIcon.gridx = 0;
		gbc_stopIcon.gridy = 0;
		homeContent.add(stopIcon, gbc_stopIcon);

		soundsScreen = new JPanel();
		soundsScreen.setBackground(Color.WHITE);
		soundsScreen.setVisible(false);
		soundsScreen.setBounds(180, 0, 720, 510);
		global.add(soundsScreen);
		soundsScreen.setLayout(null);

		soundsContent = new JPanel();
		soundsContent.setBackground(Color.WHITE);
		soundsContent.setBounds(0, 120, 720, 390);
		soundsScreen.add(soundsContent);
		soundsContent.setLayout(null);

		soundsTable = new JTable();

		scrollPane = new JScrollPane(soundsTable);
		scrollPane.setBounds(0, 90, 720, 300);
		soundsContent.add(scrollPane);
		scrollPane.setBackground(Color.WHITE);

		addSoundPanel = new JPanel();
		addSoundPanel.setBackground(Color.WHITE);
		addSoundPanel.setBounds(0, 0, 720, 120);
		soundsContent.add(addSoundPanel);
		addSoundPanel.setLayout(null);

		addSoundButton = new JButton("Ajouter un son");
		addSoundButton.setBounds(243, 25, 228, 40);
		addSoundButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addSoundPanel.add(addSoundButton);
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

		soundsHeader = new JPanel();
		soundsHeader.setBounds(0, 0, 720, 120);
		soundsScreen.add(soundsHeader);
		GridBagLayout gbl_soundsHeader = new GridBagLayout();
		gbl_soundsHeader.columnWidths = new int[] { 720, 0 };
		gbl_soundsHeader.rowHeights = new int[] { 120, 0 };
		gbl_soundsHeader.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_soundsHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		soundsHeader.setLayout(gbl_soundsHeader);

		soundsHeaderLabel = new JLabel("Mes sons");
		soundsHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		soundsHeaderLabel.setAlignmentX(0.5f);
		GridBagConstraints gbc_soundsHeaderLabel = new GridBagConstraints();
		gbc_soundsHeaderLabel.gridwidth = 0;
		gbc_soundsHeaderLabel.gridheight = 0;
		gbc_soundsHeaderLabel.gridx = 0;
		gbc_soundsHeaderLabel.gridy = 0;
		soundsHeader.add(soundsHeaderLabel, gbc_soundsHeaderLabel);

		settingsScreen = new JPanel();
		settingsScreen.setBackground(Color.WHITE);
		settingsScreen.setVisible(false);
		settingsScreen.setBounds(180, 0, 720, 510);
		global.add(settingsScreen);
		settingsScreen.setLayout(null);

		settingsContent = new JPanel();
		settingsContent.setBackground(Color.WHITE);
		settingsContent.setBounds(0, 120, 720, 390);
		settingsScreen.add(settingsContent);
		settingsContent.setLayout(null);

		emailLabel = new JLabel("Email :");
		emailLabel.setBounds(20, 50, 82, 29);
		settingsContent.add(emailLabel);
		emailLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

		userEmail = new JTextField();
		userEmail.setBounds(220, 50, 350, 28);
		settingsContent.add(userEmail);
		userEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userEmail.setBackground(Color.WHITE);
		userEmail.setColumns(10);

		notifyLabel = new JLabel("Notification :");
		notifyLabel.setBounds(20, 100, 157, 29);
		settingsContent.add(notifyLabel);
		notifyLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

		notificationCheckBox = new JCheckBox("");
		notificationCheckBox.setBounds(220, 104, 21, 21);
		settingsContent.add(notificationCheckBox);
		notificationCheckBox.setBackground(Color.WHITE);

		keyLabel = new JLabel("Cl\u00E9 :");
		keyLabel.setBounds(20, 150, 54, 29);
		settingsContent.add(keyLabel);
		keyLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

		emailKey = new JPasswordField();
		emailKey.setBounds(220, 150, 350, 28);
		settingsContent.add(emailKey);
		emailKey.setFont(new Font("Tahoma", Font.PLAIN, 18));

		saveSettingsButton = new JButton("Enregistrer ces param\u00E8tres");
		saveSettingsButton.setBounds(200, 310, 350, 39);
		settingsContent.add(saveSettingsButton);
		saveSettingsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Don't forget to set the threshold (0 for now)
				float threshold = amplitudeInput.getValue() / 100f;
				System.out.println("Threshold is : " + threshold);
				Settings settings = new Settings(userEmail.getText(), threshold, notificationCheckBox.isSelected(),
						String.valueOf(emailKey.getPassword()));
				try {
					ctrl.SaveSettings(settings);
					JOptionPane.showMessageDialog(global, "Les paramètres ont bien été sauvegardés.");
				} catch (AddressException error) {
					JOptionPane.showMessageDialog(null, "L'adresse mail est incorrecte.", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		saveSettingsButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		amplitudeInput = new JSlider();
		amplitudeInput.setBounds(220, 200, 350, 22);
		settingsContent.add(amplitudeInput);

		amplitudeLabel = new JLabel("Sensibilit\u00E9");
		amplitudeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		amplitudeLabel.setBounds(20, 200, 132, 29);
		settingsContent.add(amplitudeLabel);

		sliderMinLabel = new JLabel("peu sensible");
		sliderMinLabel.setBounds(488, 232, 82, 13);
		settingsContent.add(sliderMinLabel);

		sliderMaxLabel = new JLabel("tr\u00E8s sensible");
		sliderMaxLabel.setBounds(220, 232, 99, 13);
		settingsContent.add(sliderMaxLabel);

		settingsHeader = new JPanel();
		settingsHeader.setBounds(0, 0, 720, 120);
		settingsScreen.add(settingsHeader);
		GridBagLayout gbl_settingsHeader = new GridBagLayout();
		gbl_settingsHeader.columnWidths = new int[] { 210, 0 };
		gbl_settingsHeader.rowHeights = new int[] { 44, 0 };
		gbl_settingsHeader.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_settingsHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		settingsHeader.setLayout(gbl_settingsHeader);

		settingsHeaderLabel = new JLabel("Param\u00E8tres");
		settingsHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		GridBagConstraints gbc_settingsHeaderLabel = new GridBagConstraints();
		gbc_settingsHeaderLabel.gridwidth = 0;
		gbc_settingsHeaderLabel.gridheight = 0;
		gbc_settingsHeaderLabel.gridx = 0;
		gbc_settingsHeaderLabel.gridy = 0;
		settingsHeader.add(settingsHeaderLabel, gbc_settingsHeaderLabel);

		reloadSoundTable();
		reloadRecordsTable();
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
		JLabel[] bar = { barHome, barSounds, barHistory, barSettings };
		JPanel[] screens = { homeScreen, soundsScreen, historyScreen, settingsScreen };
		for (int i = 0; i < screens.length; i++) {
			if (screen.equals(screens[i])) {
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
	
	public void reloadRecordsTable() {
		List<Record> records = ctrl.GetRecords();
		DefaultTableModel model = (DefaultTableModel) recordsTable.getModel();
		for (int i = 0; i<records.size(); i++) {
			model.addRow(new Object[] {records.get(i).getBeginDate(), records.get(i).getEndDate(), records.get(i).getEvents().size()});
		}
		
		historyScreen.repaint();
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
	private JPanel soundsScreen;
	private JPanel historyScreen;
	private JPanel settingsScreen;
	private JTable soundsTable;
	private JScrollPane scrollPane;
	private JLabel emailLabel;
	private JTextField userEmail;
	private JCheckBox notificationCheckBox;
	private JLabel keyLabel;
	private JPasswordField emailKey;
	private JButton saveSettingsButton;
	private JPanel homeHeader;
	private JLabel playStatus;
	private JLabel playIcon;
	private JLabel stopIcon;
	private JPanel soundsHeader;
	private JLabel soundsHeaderLabel;
	private JPanel soundsContent;
	private JButton addSoundButton;
	private JPanel addSoundPanel;
	private JPanel historyHeader;
	private JPanel historyContent;
	private JLabel historyHeaderLabel;
	private JPanel settingsContent;
	private JPanel settingsHeader;
	private JLabel settingsHeaderLabel;
	private JPanel homeContent;
	private JLabel notifyLabel;
	private JSlider amplitudeInput;
	private JLabel amplitudeLabel;
	private JLabel sliderMinLabel;
	private JLabel sliderMaxLabel;
	private JTable recordsTable;
	private JScrollPane historyScrollPanel;
}

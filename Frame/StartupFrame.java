package Frame;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import org.powerbot.script.rt4.ClientContext;

import Factory.SettingsFactory;
import constants.ItemConstants;
import tasks.Task;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

public class StartupFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private boolean startScript;
	
	private List<Task<?>> taskList;
	
	private ClientContext clientContext;
	
	private JComboBox<String> cmbEssence;
	private JComboBox<String> cmbLocation;
	
	public boolean readyToStartScritp(){
		
		return startScript;
	}

	/**
	 * Create the frame.
	 */
	public StartupFrame(ClientContext clientContext, List<Task<?>> taskList) {
		this.taskList = taskList;
		this.clientContext = clientContext;
		
		initComponents();
	}

	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBosRunecrafter = new JLabel("BOS Runecrafter");
		lblBosRunecrafter.setBounds(5, 11, 289, 14);
		lblBosRunecrafter.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBosRunecrafter);
		
		JLabel lblSelectPlace = new JLabel("Essence:");
		lblSelectPlace.setBounds(25, 36, 55, 33);
		contentPane.add(lblSelectPlace);
		
		String[] optionsEssence = {"Normal","Pure"};
		cmbEssence = new JComboBox<String>(optionsEssence);
		cmbEssence.setBounds(101, 42, 114, 20);
		contentPane.add(cmbEssence);
		
		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startScript();
			}
		});
		btnNewButton.setBounds(203, 118, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(25, 80, 86, 23);
		contentPane.add(lblLocation);
		
		cmbLocation = new JComboBox<String>();
		String[] optionsLocation = {"Air","Fire","Fire (duel ring)","Body","Nature (store)"};
		cmbLocation = new JComboBox<String>(optionsLocation);
		cmbLocation.setBounds(101, 81, 114, 20);
		contentPane.add(cmbLocation);
		
		loadSettings();
	}
	
	private void loadSettings(){
		
		
		try {
		Properties	propertiesIn = new Properties();
		File file = new File(clientContext.controller.script().getStorageDirectory(), "BOSRuneCrafter.properties");
		FileInputStream in = new FileInputStream(file);

		propertiesIn.load(in);
			String essence = propertiesIn.getProperty("essence");
			String location = propertiesIn.getProperty("location");
			
			
			if(essence != null)
				cmbEssence.setSelectedItem(essence);
			
			if(location != null)
				cmbLocation.setSelectedItem(location);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void saveSettings(){
		
		try {
			OutputStream out = new FileOutputStream(new File(clientContext.controller.script().getStorageDirectory(), "BOSRuneCrafter.properties"));
			Properties propertiesOut = new Properties();
			propertiesOut.setProperty("essence", (String) cmbEssence.getSelectedItem());
			propertiesOut.setProperty("location", (String) cmbLocation.getSelectedItem());

		propertiesOut.store(out, "Properties BOS Runecrafter");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void startScript(){
		//airRunes
		saveSettings();
		SettingsFactory settingsFacotry = new SettingsFactory(clientContext);
		
		int essence = ItemConstants.ESSENCE_NORMAL;
		if(String.valueOf(cmbEssence.getSelectedItem()) == "Pure")
			essence = ItemConstants.ESSENCE_PURE;
		
		settingsFacotry.setEssence(essence);
		
		if(String.valueOf(cmbLocation.getSelectedItem()) == "Fire")
			taskList.addAll(settingsFacotry.fireRunesSettings());
		else if (String.valueOf(cmbLocation.getSelectedItem()) == "Fire (duel ring)")
			taskList.addAll(settingsFacotry.fireRingRunesSettings());
		else if (String.valueOf(cmbLocation.getSelectedItem())  == "Nature (store)")
			taskList.addAll(settingsFacotry.natureRunesSettings());
		else if(String.valueOf(cmbLocation.getSelectedItem()) == "Body")
			taskList.addAll(settingsFacotry.bodyRunesSettings());
		else
			taskList.addAll(settingsFacotry.airRunesSettings());
		
		
		this.startScript = true;
		this.setVisible(false);
	}
}

package controller;

import java.io.IOException;


import java.util.List;

import javax.mail.internet.AddressException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dao.Database;
import dao.RecordDao;
import dao.SettingsDao;
import dao.SoundDao;
import model.*;
import model.Record;

public class Controller {
	private SoundDao soundDao = new SoundDao();
	private SettingsDao settingsDao = new SettingsDao();
	private RecordDao recordDao = new RecordDao();
	private static List<Sound> sounds;
	private Recorder recorder;

	public Controller() {
		Database database = new Database();
		database.CreateTableSounds();
		database.CreateTableSettings();
		database.CreateTableRecords();
		database.CreateTableEvents();
		SetExistingSounds();
		recorder = null;
	}

	/*
	 * Sounds function
	 */

	private void SetExistingSounds() {
		Controller.sounds = soundDao.GetSounds();
	}

	public void AddSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		for (int i = 0; i < sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				System.out.println("The specified file has already been added.");
				return;
			}
		}
		Sound s;
		s = new Sound(filePath);
		soundDao.AddSound(s);
		Controller.sounds.add(s);
	}

	public void PlaySound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		for (int i = 0; i < sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				sounds.get(i).resumeAudio();
				System.out.println("Playing sound " + filePath);
			}
		}
	}

	public void DeleteSound(String filePath) {
		int index = -1;
		for (int i = 0; i < sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				index = i;
			}
		}
		if (index == -1) {
			System.out.println("The specified filePath doesn't exists.");
			return;
		}
		sounds.remove(index);
		soundDao.DeleteSound(filePath);
	}

	public List<Sound> GetSounds() {

		return Controller.sounds;
	}

	/*
	 * Record function
	 */

	public void Record() {
		Settings settings = this.GetSettings();
		recorder = new Recorder(settings, sounds);
		recorder.start();
		try {
			Runtime.getRuntime().exec("powershell.exe ./SetVolume.ps1 100");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PreventSleep();
	}

	public void StopRecord() {
		Record result = recorder.StopRecord();
		recordDao.AddRecord(result);
		recorder = null;
		AllowSleep();
		try {
			Runtime.getRuntime().exec("powershell.exe ./SetVolume.ps1 10");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Record> GetRecords(){
		return recordDao.GetRecords();
	}
	
	public void AddRecord(Record record) {
		recordDao.AddRecord(record);
	}

	/*
	 * Settings function
	 */

	public Settings GetSettings() {
		return settingsDao.GetSettings();
	}

	public void SaveSettings(Settings settings) throws AddressException {
		settings.validate();
		settingsDao.SaveSettings(settings);
	}

	/*
	 * Prevent the PC to shut down while the application is running
	 */

	public void PreventSleep() {
		PowerManagement.INSTANCE.preventSleep();
	}

	public void AllowSleep() {
		PowerManagement.INSTANCE.allowSleep();
	}

}

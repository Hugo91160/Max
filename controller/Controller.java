package controller;

import java.io.IOException;

import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dao.Database;
import dao.SettingsDao;
import dao.SoundDao;
import model.*;

public class Controller {
	private SoundDao soundDao = new SoundDao();
	private SettingsDao settingsDao = new SettingsDao();
	private List<Sound> sounds;
	private static Recorder recorder = new Recorder();


	public Controller() {
		Database database = new Database();
		database.CreateTableSounds();
		database.CreateTableSettings();
		//database.CreateTableRecords();
		SetExistingSounds();
		
	}
	
	
	
	/*
	 * Sounds function
	 */
	
	private void SetExistingSounds() {
		this.sounds = soundDao.GetSounds();
	}
	
	public void AddSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		for (int i=0; i<sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				System.out.println("The specified file has already been added.");
				return;
			}
		}
		Sound s;
		s = new Sound(filePath);
		soundDao.AddSound(s);
		this.sounds.add(s);
	}
	
	public void PlaySound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		for (int i=0; i<sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				sounds.get(i).resumeAudio();
				System.out.println("Playing sound " + filePath);
			}
		}
	}
	
	public void PlayRandomSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (this.sounds.size() == 0) {
			System.out.println("Warning: no sounds given.");
			return;
		}		
		
		Random rand = new Random();
		int index = rand.nextInt(this.sounds.size());
		this.sounds.get(index).resumeAudio();

	}
	
	public void DeleteSound(String filePath) {
		int index = -1;
		for (int i=0; i<sounds.size(); i++) {
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
		
		return this.sounds;
	}
	
	
	/*
	 * Record function
	 */
	
	public void Record() {
		recorder.start();
	}
	
	public void StopRecord() {
		recorder.StopRecord();
		recorder = new Recorder();
	}
	
	
	/*
	 * Settings function
	 */
	
	public Settings GetSettings() {
		return settingsDao.GetSettings();
	}
	
	public void SaveSettings(Settings settings) {
		settingsDao.SaveSettings(settings);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

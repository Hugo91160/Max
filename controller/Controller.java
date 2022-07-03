package controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import dao.Database;
import dao.SoundDao;
import model.*;

public class Controller {
	private SoundDao soundDao = new SoundDao();
	private static List<Sound> sounds;
	private static Recorder recorder = new Recorder();


	public Controller() {
		Database database = new Database();
		database.CreateTableSounds();
		//database.CreateTableRecords();
		SetExistingSounds();
		
	}
	
	
	
	/*
	 * Sounds function
	 */
	
	private void SetExistingSounds() {
		this.sounds = soundDao.GetSounds();
	}
	
	public void AddSound(String filePath) {
		for (int i=0; i<sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				System.out.println("The specified file has already been added.");
				return;
			}
		}
		Sound s;
		try {
			s = new Sound(filePath);
			soundDao.AddSound(s);
			this.sounds.add(s);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void PlaySound(String filePath) {
		for (int i=0; i<sounds.size(); i++) {
			if (sounds.get(i).getFilePath().contentEquals(filePath)) {
				try {
					sounds.get(i).resumeAudio();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Playing sound " + filePath);
			}
		}
	}
	
	public void PlayRandomSound() {
		if (this.sounds.size() == 0) {
			System.out.println("Warning: no sounds given.");
			return;
		}
		
		
		Random rand = new Random();
		int index = rand.nextInt(this.sounds.size());
		try {
			this.sounds.get(index).resumeAudio();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

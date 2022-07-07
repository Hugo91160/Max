package model;

// Java program to play an Audio
// file using Clip Object
import java.io.File;


import java.io.IOException;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	// to store current position
	private Long currentFrame = 0L;
	private Clip clip;

	// current status of clip
	private String status = "paused";

	private AudioInputStream audioInputStream;
	private String filePath;
	private String fileName;
	private Date date;

	// constructor to initialize streams and clip
	public Sound(String soundFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.filePath = soundFilePath;
		// create AudioInputStream object
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

		// create clip reference
		clip = AudioSystem.getClip();

		// open audioInputStream to the clip
		clip.open(audioInputStream);
		

		date = new Date();
		fileName = this.GetFilename();

	}

	/*
	 * Class methods
	 */

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Method to play the audio
	public void play() {
		// start the clip
		clip.start();
		status = "play";
	}

	public void pause() {
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	// Method to restart the audio
    public void restart() throws IOException, LineUnavailableException,
                                            UnsupportedAudioFileException 
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException,
                                IOException, LineUnavailableException 
    {
    	/*
        if (status.equals("play")) 
        {
            System.out.println("Audio is already "+
            "being played");
            return;
        }
        */
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

	// Method to reset audio stream
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip.open(audioInputStream);
	}

	public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		currentFrame = 0L;
		clip.stop();
		clip.close();
		status = "stop";
	}

	private String GetFilename() {
		String res = "";
		if (this.filePath.contentEquals("") || this.filePath.isEmpty()) {
			return null;
		}

		int lastIndexOfBackslash = 0;
		for (int i = 0; i < this.filePath.length(); i++) {
			if (this.filePath.charAt(i) == '\\') {
				lastIndexOfBackslash = i;
			}
		}
		res = this.filePath.substring(lastIndexOfBackslash + 1);

		return res;
	}

	/*
	 * Getters and setters
	 */

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(Long currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AudioInputStream getAudioInputStream() {
		return audioInputStream;
	}

	public void setAudioInputStream(AudioInputStream audioInputStream) {
		this.audioInputStream = audioInputStream;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.Settings;
import model.Sound;
import model.Event;
import model.Record;
import utils.Chronometer;

public class Recorder extends Thread {
	private volatile boolean stopRecord;
	private static MailSender mailSender = new MailSender();
	private float amplitudeThreshold;
	private Settings settings;
	private List<Sound> sounds;
	private Record record;

	public Recorder(Settings settings, List<Sound> sounds) {
		this.stopRecord = false;
		this.settings = settings;
		this.sounds = sounds;
		this.record = new Record();
		amplitudeThreshold = settings.getAmplitudeThreshold();
	}

	public Record StopRecord() {
		this.stopRecord = true;
		this.record.setEndDate(LocalDateTime.now());
		return this.record;
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Recording...");
		AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
		final int bufferByteSize = 2048;

		TargetDataLine line;
		try {
			line = AudioSystem.getTargetDataLine(fmt);
			line.open(fmt, bufferByteSize);
		} catch (LineUnavailableException e) {
			System.err.println(e);
			return;
		}

		byte[] buf = new byte[bufferByteSize];
		float[] samples = new float[bufferByteSize / 2];

		line.start();
		while (this.stopRecord == false) {
			int b = line.read(buf, 0, buf.length);
			for (int i = 0, s = 0; i < b;) {
				int sample = 0;

				sample |= buf[i++] & 0xFF; // (reverse these two lines
				sample |= buf[i++] << 8; // if the format is big endian)

				// normalize to range of +/-1.0f
				samples[s++] = sample / 32768f;
			}

			float rms = 0f;
			float peak = 0f;
			for (float sample : samples) {

				float abs = Math.abs(sample);
				if (abs > peak) {
					peak = abs;
				}

				rms += sample * sample;
			}

			rms = (float) Math.sqrt(rms / samples.length);

			if (peak > amplitudeThreshold) {
				Chronometer chrono = new Chronometer();
				chrono.start();
				System.out.println("Detected sound");
				Event event = new Event(this.record.getId(), LocalDateTime.now());
				this.record.AddEvent(event);
				Sound soundPlayed = null;
				try {
					soundPlayed = PlayRandomSound();
					if (settings.isNotifyByEmail()) {
						mailSender.send();
					}
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				chrono.stop();

				if (soundPlayed != null) {
					long sleepRemaining = soundPlayed.getDurationInSeconds() * 1000
							- chrono.getEllapsedTimeMilliseconds() < 0 ? 0
									: (long) soundPlayed.getDurationInSeconds() * 1000
											- chrono.getEllapsedTimeMilliseconds();

					try {
						System.out.println("Sleep duration : " + sleepRemaining);
						sleep(sleepRemaining);
						System.out.println("Sleep is over");
						// sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}
		line.close();
		System.out.println("Recording has been stopped.");

	}

	private Sound PlayRandomSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (this.sounds.size() == 0) {
			System.out.println("Warning: no sounds given.");
			return null;
		}

		Random rand = new Random();
		int index = rand.nextInt(this.sounds.size());
		this.sounds.get(index).resumeAudio();

		return this.sounds.get(index);

	}

}

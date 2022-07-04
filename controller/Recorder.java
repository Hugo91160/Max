package controller;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Recorder extends Thread {
	private volatile boolean stopRecord;
	private static Controller ctrl;

	public Recorder() {
        this.stopRecord = false;
        ctrl = new Controller();
    }
	
	public void StopRecord() {
		this.stopRecord = true;
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
        } catch(LineUnavailableException e) {
            System.err.println(e);
            return;
        }

        byte[] buf = new byte[bufferByteSize];
        float[] samples = new float[bufferByteSize / 2];

        line.start();
		while (this.stopRecord == false) {
			int b = line.read(buf, 0, buf.length);
            for(int i = 0, s = 0; i < b;) {
                int sample = 0;

                sample |= buf[i++] & 0xFF; // (reverse these two lines
                sample |= buf[i++] << 8;   //  if the format is big endian)

                // normalize to range of +/-1.0f
                samples[s++] = sample / 32768f;
            }

            float rms = 0f;
            float peak = 0f;
            for(float sample : samples) {

                float abs = Math.abs(sample);
                if(abs > peak) {
                    peak = abs;
                }

                rms += sample * sample;
            }

            rms = (float)Math.sqrt(rms / samples.length);

            if (peak > 0.35f) {
            	System.out.println("Detected sound");
				try {
					ctrl.PlayRandomSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            	try {
					sleep(7000);					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
		}
		line.close();
		System.out.println("stopped");
		
	}
	
}

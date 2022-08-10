package utils;

public class Chronometer {
	private long startTime;
	private long stopTime;
	
	public Chronometer() {
		reset();
	}
	
	public void reset() {
		this.startTime = 0;
		this.stopTime = 0;
	}
	
	public void start() {
		this.startTime = System.nanoTime();
	}
	
	public void stop() {
		this.stopTime = System.nanoTime();
	}
	
	public long getEllapsedTimeMilliseconds() {
		return (this.stopTime - this.startTime)/1000000;
	}
}

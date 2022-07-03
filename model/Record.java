package model;

import java.util.Date;
import java.util.List;

public class Record {
	private int id;
	private Date beginDate;
	private Date endDate;
	private List<Event> events;
	
	public Record() {
		
	}
	
	
	/*
	 * Class methods
	 */
	
	
	/*
	 * Getters and setters
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}

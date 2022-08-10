package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Record {
	private String id;
	private LocalDateTime beginDate;
	private LocalDateTime endDate;
	private List<Event> events;
	
	public Record() {
		this.id = UUID.randomUUID().toString();
		this.beginDate = LocalDateTime.now();
		this.events = new ArrayList<Event>();
	}
	
	public Record(String id, LocalDateTime beginDate, LocalDateTime endDate, List<Event> events) {
		super();
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.events = events;
	}
	
	
	/*
	 * Class methods
	 */
	
	public void AddEvent(Event event) {
		this.events.add(event);
	}
	
	
	/*
	 * Getters and setters
	 */




	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public LocalDateTime getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(LocalDateTime beginDate) {
		this.beginDate = beginDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}

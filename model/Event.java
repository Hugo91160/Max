package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class Event {
	private String id;
	private String recordId;
	private LocalDateTime date;
	
	public Event(String recordId, LocalDateTime date) {
		this.id = UUID.randomUUID().toString();
		this.recordId = recordId;
		this.date = date;
	}
	
	public Event(String id, String recordId, LocalDateTime date) {
		this.id = id;
		this.recordId = recordId;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}

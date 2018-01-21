package com.partybnb.eventregistration.dto;

import java.sql.Date;
import java.sql.Time;

import com.partybnb.eventregistration.model.Location;

public class EventDto {
	private String name;
	private Date eventDate;
	private Time startTime;
	private Time endTime;
	private String organizer;
	private Location loc;
	private int rating;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public EventDto() {

	}

	public EventDto(String name) {
		this(name, Date.valueOf("1971-01-01"), Time.valueOf("00:00:00"), Time.valueOf("23:59:59"), null, null, 0);
	}

	public EventDto(String name, Date eventDate, Time startTime, Time endTime, ParticipantDto p, Location loc, int rating) {
		this.name = name;
		this.eventDate = eventDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.organizer = p.getName();
		this.loc = loc;
		this.rating = rating;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Time getEndTime() {
		return endTime;
	}		


	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

}

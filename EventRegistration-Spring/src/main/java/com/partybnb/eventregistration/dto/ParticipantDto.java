package com.partybnb.eventregistration.dto;

import java.util.ArrayList;
import java.util.List;

import com.partybnb.eventregistration.model.Location;

public class ParticipantDto {
	private String name;
	private List<EventDto> events;
	private Location loc;
	private String username;
	private String password;

	public ParticipantDto() {
	}

	public ParticipantDto(String name) {
		this(name, new ArrayList<EventDto>(), new Location(0, 0), null, null);
	}

	public ParticipantDto(String name, ArrayList<EventDto> arrayList, Location loc, String username, String password) {
		this.name = name;
		this.events = arrayList;
		this.loc= loc;
		this.username = username;
		this.password = password;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EventDto> getEvents() {
		return events;
	}

	public void setEvents(List<EventDto> events) {
		this.events = events;
	}

}

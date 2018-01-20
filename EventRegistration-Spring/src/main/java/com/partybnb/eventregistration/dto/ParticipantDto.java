package com.partybnb.eventregistration.dto;

import java.util.ArrayList;
import java.util.List;

import com.partybnb.eventregistration.model.Participant;
import com.partybnb.eventregistration.model.Registration;
import com.partybnb.eventregistration.model.RegistrationManager;

public class ParticipantDto {
	private String name;
	private List<EventDto> events;

	public ParticipantDto() {
	}

	public ParticipantDto(String name) {
		this(name, new ArrayList<EventDto>());
	}

	public ParticipantDto(String name, ArrayList<EventDto> arrayList) {
		this.name = name;
		this.events = arrayList;
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

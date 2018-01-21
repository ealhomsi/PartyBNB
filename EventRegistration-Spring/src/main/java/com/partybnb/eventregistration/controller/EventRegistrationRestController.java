package com.partybnb.eventregistration.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.partybnb.eventregistration.dto.EventDto;
import com.partybnb.eventregistration.dto.ParticipantDto;
import com.partybnb.eventregistration.dto.RegistrationDto;
import com.partybnb.eventregistration.model.Event;
import com.partybnb.eventregistration.model.Location;
import com.partybnb.eventregistration.model.Participant;
import com.partybnb.eventregistration.model.Registration;
import com.partybnb.eventregistration.service.EventRegistrationService;
import com.partybnb.eventregistration.service.InvalidInputException;

@RestController
public class EventRegistrationRestController {

	@Autowired
	private EventRegistrationService service;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * SHOW
	 */
	@GetMapping(value = { "/participants/{name}", "/participants/{name}/" })
	public ParticipantDto showParticipant(@PathVariable("name") String name) throws InvalidInputException {
		return convertToDto(service.findParticipant(name));
	}

	@GetMapping(value = { "/events/{name}", "/events/{name}/" })
	public EventDto showEvent(@PathVariable("name") String name) throws InvalidInputException {
		Event event = service.findEvent(name);
		return convertToDto(event);
	}

	@GetMapping(value = { "/events", "/events/" })
	public List<EventDto> findAllEvents() throws InvalidInputException {
		List<EventDto> events = new ArrayList<>();
		for (Event e : service.findAllEvents()) {
			events.add(convertToDto(e));
		}
		return events;
	}
	

	@GetMapping(value = { "/events/{name}/creator", "/events/{name}/creator/" })
	public ParticipantDto findEventCreator(@PathVariable("name") String name) throws InvalidInputException {
		return convertToDto(service.findEvent(name).getOrganizer());
	}


	@GetMapping(value = { "/pariticipant/{name}/createdevents", "/pariticipant/{name}/createdevents/" })
	public List<EventDto> findCreatedEvents(@PathVariable("name") String name) throws InvalidInputException {
		List<EventDto> list = new ArrayList<>();
		
		for(Event e: service.createdBy(service.findParticipant(name))) {
			list.add(convertToDto(e));
		}
		
		return list;
	}
	
	@GetMapping(value = { "/participants", "/participants/" })
	public List<ParticipantDto> findAllParticipants() {
		List<ParticipantDto> participants = new ArrayList<>();
		for (Participant participant : service.findAllParticipants()) {
			participants.add(convertToDto(participant));
		}
		return participants;
	}
	
	@GetMapping(value = { "/participants/locations/{name}", "/participants/locations/{name}/" })
	public Location findLocationOfParticipant(@PathVariable("name") String name) throws InvalidInputException {
		Participant p = service.findParticipant(name);
		return p.getLoc();
	}
	
	@GetMapping(value = { "/events/locations/{name}", "/events/locations/{name}/" })
	public Location findLocationOfEvent (@PathVariable("name") String name) throws InvalidInputException {
		Event p = service.findEvent(name);
		return p.getLoc();
	}


	@RequestMapping("/")
	public String index() {
		return "Event registration application root. Web-based frontend is a TODO. Use the REST API to manage events and participants.\n";
	}

	@GetMapping(value = { "/registrations/participant/{name}", "/registrations/participant/{name}/" })
	public List<EventDto> getEventsOfParticipant(@PathVariable("name") ParticipantDto pDto) {
		Participant p = convertToDomainObject(pDto);
		return createEventDtosForParticipant(p);
	}

	/**
	 * CREATE
	 */

	@PostMapping(value = { "/participants/{name}", "/participants/{name}/" })
	public ParticipantDto createParticipant(@PathVariable("name") String name, @RequestParam String username,
			@RequestParam String password, @RequestParam double lon, @RequestParam double lan)
			throws InvalidInputException {
		String username2 = "" + username;
		String password2 = "" + password;
		Participant participant = service.createParticipant(name, username2, password2, new Location(lon, lan));
		return convertToDto(participant);
	}

	@PostMapping(value = { "/events/{name}", "/events/{name}/" })
	public EventDto createEvent(@PathVariable("name") String name, @RequestParam Date date,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime startTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm") LocalTime endTime,
			@RequestParam int rating, @RequestParam double lon, @RequestParam double lat, @RequestParam String username) throws InvalidInputException {
		@SuppressWarnings("deprecation")
		Time startTimeSql = new Time(startTime.getHour(), startTime.getMinute(), 0);
		@SuppressWarnings("deprecation")
		Time endTimeSql = new Time(endTime.getHour(), endTime.getMinute(), 0);
		Event event = service.createEvent(name, date, startTimeSql, endTimeSql, rating, new Location(lon, lat), service.findParticipant(username));
		return convertToDto(event);
	}

	@PostMapping(value = { "/register", "/register/" })
	public RegistrationDto registerParticipantForEvent(@RequestParam(name = "participant") ParticipantDto pDto,
			@RequestParam(name = "event") EventDto eDto) throws InvalidInputException {
		// In this example application, we assumed that participants and events are
		// identified by their names
		Participant p = service.findParticipant(pDto.getName());
		Event e = service.findEvent(eDto.getName());
		Registration r = service.register(p, e);
		return convertToDto(r, p, e);
	}

	// Conversion methods (not part of the API)
	private EventDto convertToDto(Event e) {
		// In simple cases, the mapper service is convenient
		return modelMapper.map(e, EventDto.class);
	}

	private ParticipantDto convertToDto(Participant p) {
		ParticipantDto participantDto = modelMapper.map(p, ParticipantDto.class);
		participantDto.setEvents(createEventDtosForParticipant(p));
		return participantDto;
	}

	private Participant convertToDomainObject(ParticipantDto pDto) {
		// Mapping DTO to the domain object without using the mapper
		List<Participant> allParticipants = service.findAllParticipants();
		for (Participant participant : allParticipants) {
			if (participant.getName().equals(pDto.getName())) {
				return participant;
			}
		}
		return null;
	}

	private List<EventDto> createEventDtosForParticipant(Participant p) {
		// TODO it is your task to add and implement the getEventsForParticipant service
		// within the EventRegistrationService class, since it is missing now
		List<Event> eventsForParticipant = service.getEventsForParticipant(p);
		List<EventDto> events = new ArrayList<EventDto>();
		for (Event event : eventsForParticipant) {
			events.add(convertToDto(event));
		}
		return events;
	}

	private RegistrationDto convertToDto(Registration r, Participant p, Event e) {
		// Now using the mapper would not help too much
		// RegistrationDto registrationDto = modelMapper.map(r, RegistrationDto.class);
		// Manual conversion instead
		EventDto eDto = convertToDto(e);
		ParticipantDto pDto = convertToDto(p);
		return new RegistrationDto(pDto, eDto);
	}
}

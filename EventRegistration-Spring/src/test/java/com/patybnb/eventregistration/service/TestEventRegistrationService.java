package com.patybnb.eventregistration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.partybnb.eventregistration.model.Event;
import com.partybnb.eventregistration.model.Location;
import com.partybnb.eventregistration.model.Participant;
import com.partybnb.eventregistration.model.Registration;
import com.partybnb.eventregistration.model.RegistrationManager;
import com.partybnb.eventregistration.persistence.PersistenceXStream;
import com.partybnb.eventregistration.service.EventRegistrationService;
import com.partybnb.eventregistration.service.InvalidInputException;

public class TestEventRegistrationService {

	private RegistrationManager rm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PersistenceXStream.initializeModelManager("output" + File.separator + "data.xml");
	}

	@Before
	public void setUp() throws Exception {
		rm = new RegistrationManager();
	}

	@After
	public void tearDown() throws Exception {
		rm.delete();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	@Test
	public void testRegisterParticipantAndEventDoNotExist() {
	    assertEquals(0, rm.getRegistrations().size());

	    String nameP = "Oscar";
	    Participant participant = new Participant(nameP, "oscar", "123", new Location(0, 0));
	    assertEquals(0, rm.getParticipants().size());

	    String nameE = "Soccer Game";
	    Calendar c = Calendar.getInstance();
	    c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
	    Date eventDate = new Date(c.getTimeInMillis());
	    Time startTime = new Time(c.getTimeInMillis());
	    c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
	    Time endTime = new Time(c.getTimeInMillis());
	    Event event = new Event(nameE, eventDate, startTime, endTime, 3, new Location(0, 0), null);
	    assertEquals(0, rm.getEvents().size());

	    String error = null;
	    EventRegistrationService erc = new EventRegistrationService(rm);
	    try {
	        erc.register(participant, event);
	    } catch (InvalidInputException e) {
	        error = e.getMessage();
	    }

	    // check error
	    assertEquals("Participant does not exist! Event does not exist!", error);

	    // check model in memory
	    assertEquals(0, rm.getRegistrations().size());
	    assertEquals(0, rm.getParticipants().size());
	    assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateParticipant() {
		assertEquals(0, rm.getParticipants().size());
		assertEquals(0, rm.getEvents().size());
		assertEquals(0, rm.getRegistrations().size());

		String participantName = "a";
		String eventName = "b";
		EventRegistrationService erc = new EventRegistrationService(rm);
		try {
			erc.createParticipant(participantName, "username", "password", new Location(0, 0));
			erc.createEvent(eventName, new Date(1), new Time(1), new Time(1), 3 , new Location(0,0), null);
		} catch (InvalidInputException e) {
			// Check that no error occured
			fail();
		}

		// check model in memory
		checkResultParticipant(participantName, rm);
		checkResultEvent(eventName, rm);
		
		rm = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
		checkResultParticipant(participantName, rm);
		checkResultEvent(eventName, rm);
	}

	private void checkResultParticipant(String name, RegistrationManager rm2) {
		assertEquals(1, rm2.getParticipants().size());
		assertEquals(name, rm2.getParticipant(0).getName());
	}
	private void checkResultEvent(String name, RegistrationManager rm2) {
		assertEquals(1, rm2.getEvents().size());
		assertEquals(name, rm2.getEvent(0).getName());
		
	}
	@Test
	public void testCreateEvent() {
	  RegistrationManager rm = new RegistrationManager();
	  assertEquals(0, rm.getEvents().size());

	  String name = "Soccer Game";
	  Calendar c = Calendar.getInstance();
	  c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
	  Date eventDate = new Date(c.getTimeInMillis());
	  Time startTime = new Time(c.getTimeInMillis());
	  c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
	  Time endTime = new Time(c.getTimeInMillis());
	  // test model in memory
	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	    erc.createEvent(name, eventDate, startTime, endTime, 3, new Location(0, 0),null);
	  } catch (InvalidInputException e) {
	    fail();
	  }
	  checkResultEvent(name, eventDate, startTime, endTime, rm);
	  // test file
	  RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
	  checkResultEvent(name, eventDate, startTime, endTime, rm2);
	  rm2.delete();
	}
	
	
	@Test
	public void testRegister() {
	  RegistrationManager rm = new RegistrationManager();
	  assertEquals(0, rm.getRegistrations().size());

	  String nameP = "Oscar";
	  Participant participant = new Participant(nameP, "username", "password", new Location(0,0));
	  rm.addParticipant(participant);
	  assertEquals(1, rm.getParticipants().size());

	  String nameE = "Soccer Game";
	  Calendar c = Calendar.getInstance();
	  c.set(2017, Calendar.MARCH, 16, 9, 0, 0);
	  Date eventDate = new Date(c.getTimeInMillis());
	  Time startTime = new Time(c.getTimeInMillis());
	  c.set(2017, Calendar.MARCH, 16, 10, 30, 0);
	  Time endTime = new Time(c.getTimeInMillis());
	  Event event = new Event(nameE, eventDate, startTime, endTime, 3, new Location(0,0), null);
	  rm.addEvent(event);
	  assertEquals(1, rm.getEvents().size());

	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	    erc.register(participant, event);
	  } catch (InvalidInputException e) {
	    fail();
	  }
	  checkResultRegister(nameP, nameE, eventDate, startTime, endTime, rm);

	  RegistrationManager rm2 = (RegistrationManager) PersistenceXStream.loadFromXMLwithXStream();
	  // check file contents
	  checkResultRegister(nameP, nameE, eventDate, startTime, endTime, rm2);
	  rm2.delete();
	}

	
	@Test
	public void testFindAllParticipants()
	{
	    assertEquals(0, rm.getParticipants().size());

	    String[] names = { "John Doe", "Foo Bar" };

	    EventRegistrationService erc = new EventRegistrationService(rm);
	    for (String name : names) {
	        try {
	            erc.createParticipant(name, "username", "password", new Location(0,0));
	        } catch (InvalidInputException e) {
	            // Check that no error occured
	            fail();
	        }
	    }

	    List<Participant> registeredParticipants = erc.findAllParticipants();

	    // check number of registered participants
	    assertEquals(2, registeredParticipants.size());

	    // check each participant
	    for (int i = 0; i < names.length; i++) {
	        assertEquals(names[i], registeredParticipants.get(i).getName());
	    }

	}
	
	@Test
	public void testCreateEventEmpty() {
	  assertEquals(0, rm.getEvents().size());

	  String name = "";
	  Calendar c = Calendar.getInstance();
	  c.set(2017, Calendar.FEBRUARY, 16, 10, 00, 0);
	  Date eventDate = new Date(c.getTimeInMillis());
	  Time startTime = new Time(c.getTimeInMillis());
	  c.set(2017, Calendar.FEBRUARY, 16, 11, 30, 0);
	  Time endTime = new Time(c.getTimeInMillis());

	  String error = null;
	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	      erc.createEvent(name, eventDate, startTime, endTime, 3, new Location(0,0), null);
	  } catch (InvalidInputException e) {
	      error = e.getMessage();
	  }

	  // check error
	  assertEquals("Event name cannot be empty!", error);
	  // check model in memory
	  assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventNull() {
	  assertEquals(0, rm.getRegistrations().size());

	  String name = null;
	  Date eventDate = null;
	  Time startTime = null;
	  Time endTime = null;

	  String error = null;
	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	      erc.createEvent(name, eventDate, startTime, endTime, 3, new Location(0,0), null);
	  } catch (InvalidInputException e) {
	      error = e.getMessage();
	  }

	  // check error
	  assertEquals(
	          "Event name cannot be empty! Event date cannot be empty! Event start time cannot be empty! Event end time cannot be empty!",
	          error);
	  // check model in memory
	  assertEquals(0, rm.getEvents().size());
	}
	
	
	@Test
	public void testRegisterNull() {
	  assertEquals(0, rm.getRegistrations().size());

	  Participant participant = null;
	  assertEquals(0, rm.getParticipants().size());

	  Event event = null;
	  assertEquals(0, rm.getEvents().size());

	  String error = null;
	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	      erc.register(participant, event);
	  } catch (InvalidInputException e) {
	      error = e.getMessage();
	  }

	  // check error
	  assertEquals("Participant needs to be selected for registration! Event needs to be selected for registration!",
	          error);

	  // check model in memory
	  assertEquals(0, rm.getRegistrations().size());
	  assertEquals(0, rm.getParticipants().size());
	  assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventEndTimeBeforeStartTime() {
	assertEquals(0, rm.getEvents().size());

	  String name = "Soccer Game";
	  Calendar c = Calendar.getInstance();
	  c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
	  Date eventDate = new Date(c.getTimeInMillis());
	  Time startTime = new Time(c.getTimeInMillis());
	  c.set(2016, Calendar.OCTOBER, 16, 8, 59, 59);
	  Time endTime = new Time(c.getTimeInMillis());

	  String error = null;
	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	      erc.createEvent(name, eventDate, startTime, endTime, 3, new Location(0,0), null);
	  } catch (InvalidInputException e) {
	      error = e.getMessage();
	  }

	  // check error
	  assertEquals("Event end time cannot be before event start time!", error);

	  // check model in memory
	  assertEquals(0, rm.getEvents().size());
	}
	
	@Test
	public void testCreateEventSpaces() {
	  assertEquals(0, rm.getEvents().size());

	  String name = " ";
	  Calendar c = Calendar.getInstance();
	  c.set(2016, Calendar.OCTOBER, 16, 9, 00, 0);
	  Date eventDate = new Date(c.getTimeInMillis());
	  Time startTime = new Time(c.getTimeInMillis());
	  c.set(2016, Calendar.OCTOBER, 16, 10, 30, 0);
	  Time endTime = new Time(c.getTimeInMillis());

	  String error = null;
	  EventRegistrationService erc = new EventRegistrationService(rm);
	  try {
	      erc.createEvent(name, eventDate, startTime, endTime, 3, new Location(0, 0), null);
	  } catch (InvalidInputException e) {
	      error = e.getMessage();
	  }
	  // check error
	  assertEquals("Event name cannot be empty!", error);
	  // check model in memory
	  assertEquals(0, rm.getEvents().size());
	}
	
	private void checkResultRegister(String nameP, String nameE, Date eventDate, Time startTime, Time endTime,
	        RegistrationManager rm2)
	{
	  assertEquals(1, rm2.getParticipants().size());
	  assertEquals(nameP, rm2.getParticipant(0).getName());
	  assertEquals(1, rm2.getEvents().size());
	  assertEquals(nameE, rm2.getEvent(0).getName());
	  assertEquals(eventDate.toString(), rm2.getEvent(0).getEventDate().toString());
	  assertEquals(startTime.toString(), rm2.getEvent(0).getStartTime().toString());
	  assertEquals(endTime.toString(), rm2.getEvent(0).getEndTime().toString());
	  assertEquals(1, rm2.getRegistrations().size());
	  assertEquals(rm2.getEvent(0), rm2.getRegistration(0).getEvent());
	  assertEquals(rm2.getParticipant(0), rm2.getRegistration(0).getParticipant());
	}
	
	private void checkResultEvent(String name, Date eventDate, Time startTime, Time endTime, RegistrationManager rm2)   {
		  assertEquals(0, rm2.getParticipants().size());
		  assertEquals(1, rm2.getEvents().size());
		  assertEquals(name, rm2.getEvent(0).getName());
		  assertEquals(eventDate.toString(), rm2.getEvent(0).getEventDate().toString());
		  assertEquals(startTime.toString(), rm2.getEvent(0).getStartTime().toString());
		  assertEquals(endTime.toString(), rm2.getEvent(0).getEndTime().toString());
		  assertEquals(0, rm2.getRegistrations().size());
	}

}

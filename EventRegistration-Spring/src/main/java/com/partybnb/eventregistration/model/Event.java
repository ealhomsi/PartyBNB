/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package com.partybnb.eventregistration.model;
import java.sql.Date;
import java.sql.Time;

// line 15 "../../../../EventRegisteration.ump"
public class Event
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Event Attributes
  private String name;
  private Date eventDate;
  private Time startTime;
  private Time endTime;
  private int rating;

  //Event Associations
  private Location loc;
  private Participant organizer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Event(String aName, Date aEventDate, Time aStartTime, Time aEndTime, int aRating, Location aLoc, Participant aOrganizer)
  {
    name = aName;
    eventDate = aEventDate;
    startTime = aStartTime;
    endTime = aEndTime;
    rating = aRating;
    if (!setLoc(aLoc))
    {
      throw new RuntimeException("Unable to create Event due to aLoc");
    }
    if (!setOrganizer(aOrganizer))
    {
      throw new RuntimeException("Unable to create Event due to aOrganizer");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEventDate(Date aEventDate)
  {
    boolean wasSet = false;
    eventDate = aEventDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(int aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Date getEventDate()
  {
    return eventDate;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public int getRating()
  {
    return rating;
  }

  public Location getLoc()
  {
    return loc;
  }

  public Participant getOrganizer()
  {
    return organizer;
  }

  public boolean setLoc(Location aNewLoc)
  {
    boolean wasSet = false;
    if (aNewLoc != null)
    {
      loc = aNewLoc;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setOrganizer(Participant aNewOrganizer)
  {
    boolean wasSet = false;
    if (aNewOrganizer != null)
    {
      organizer = aNewOrganizer;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    loc = null;
    organizer = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "rating" + ":" + getRating()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "eventDate" + "=" + (getEventDate() != null ? !getEventDate().equals(this)  ? getEventDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "loc = "+(getLoc()!=null?Integer.toHexString(System.identityHashCode(getLoc())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "organizer = "+(getOrganizer()!=null?Integer.toHexString(System.identityHashCode(getOrganizer())):"null");
  }
}
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package com.partybnb.eventregistration.model;

// line 7 "../../../../EventRegisteration.ump"
public class Participant
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Participant Attributes
  private String name;
  private String username;
  private String password;

  //Participant Associations
  private Location loc;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Participant(String aName, String aUsername, String aPassword, Location aLoc)
  {
    name = aName;
    username = aUsername;
    password = aPassword;
    if (!setLoc(aLoc))
    {
      throw new RuntimeException("Unable to create Participant due to aLoc");
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

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public Location getLoc()
  {
    return loc;
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

  public void delete()
  {
    loc = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "loc = "+(getLoc()!=null?Integer.toHexString(System.identityHashCode(getLoc())):"null");
  }
}
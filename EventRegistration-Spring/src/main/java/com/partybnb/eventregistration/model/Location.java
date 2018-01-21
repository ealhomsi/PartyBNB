/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.26.1-f40f105-3613 modeling language!*/

package com.partybnb.eventregistration.model;

// line 3 "../../../../EventRegisteration.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private double lon;
  private double lat;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(double aLon, double aLat)
  {
    lon = aLon;
    lat = aLat;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLon(double aLon)
  {
    boolean wasSet = false;
    lon = aLon;
    wasSet = true;
    return wasSet;
  }

  public boolean setLat(double aLat)
  {
    boolean wasSet = false;
    lat = aLat;
    wasSet = true;
    return wasSet;
  }

  public double getLon()
  {
    return lon;
  }

  public double getLat()
  {
    return lat;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "lon" + ":" + getLon()+ "," +
            "lat" + ":" + getLat()+ "]";
  }
}
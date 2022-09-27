package com.getyourway.flights;

import java.util.ArrayList;

public class AirportNearbyResponse {

  public ArrayList<Airport> items;
}

class Airport {

  public String iata;
  public String icao;
  public String shortName;
  public String name; // Using reserved word, yet it still works.
  public String municipalityName;
  public Location location;
  public String countryCode;
}

class Location {
  public float lat;
  public float lon;
}

package com.getyourway.flights;

import java.util.ArrayList;

/**
 *  */
public class AirportResponseDTO {

  public ArrayList<AirportDTO> items;
}

class AirportDTO {

  public String iata;
  public String icao;
  public String shortName;
  public String name; // Using reserved word, yet it still works.
  public String municipalityName;
  public LocationDTO location;
  public String countryCode;
}

class LocationDTO {
  public float lat;
  public float lon;
}

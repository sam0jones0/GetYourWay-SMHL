package com.getyourway.flights;

import java.util.ArrayList;

public class AirportScheduleResponse {

  public ArrayList<Flight> departures;
}

class Flight {

  public Departure departure;
  public Arrival arrival;
  public String number; // Using reserved word, yet it still works.
}

class Departure {

  public String scheduledTimeLocal;
  public String scheduledTimeUtc;
}

class Arrival {

  public Airport airport;
  public String scheduledTimeLocal;
  public String scheduledTimeUtc;
}

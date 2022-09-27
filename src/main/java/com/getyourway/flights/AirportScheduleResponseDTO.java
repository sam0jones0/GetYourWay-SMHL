package com.getyourway.flights;

import java.util.ArrayList;

public class AirportScheduleResponseDTO {

  public ArrayList<FlightDTO> departures;
}

class FlightDTO {

  public DepartureDTO departure;
  public ArrivalDTO arrival;
  public String number; // Using reserved word, yet it still works.
}

class DepartureDTO {

  public String scheduledTimeLocal;
  public String scheduledTimeUtc;
}

class ArrivalDTO {

  public AirportDTO airport;
  public String scheduledTimeLocal;
  public String scheduledTimeUtc;
}

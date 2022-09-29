package com.getyourway.flights.localairportdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalAirportRepo extends JpaRepository<InternalAirport, String> {

  public Optional<List<InternalAirport>> getAirportByIcao(String icao);

  public Optional<List<InternalAirport>> getAirportByIata(String iata);

  public Optional<List<InternalAirport>> getAirportByCityContainsIgnoreCase(String city);
}

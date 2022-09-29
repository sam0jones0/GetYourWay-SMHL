package com.getyourway.flights.localairportdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalAirportRepo extends JpaRepository<InternalAirport, String> {

  public Optional<List<InternalAirport>> getAirportByIcao(String icao);

  public Optional<List<InternalAirport>> getAirportByIata(String iata);

  public Optional<List<InternalAirport>> getAirportByCityContainsIgnoreCase(String city);

  // FIXME: Query does not work.
  @Query(
      value =
          "SELECT * FROM airports where city like '%:input%' or country like '%:input%' or icao like '%:input%' or iata like '%:input%'",
      nativeQuery = true)
  public Optional<List<InternalAirport>> getByUserString(@Param("input") String searchQuery);
}

package com.getyourway.flights.localairportdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalAirportRepo extends JpaRepository<InternalAirport, String> {

  @Query("select i from InternalAirport i where i.icao = ?1")
  Optional<List<InternalAirport>> getAirportByIcao(String icao);

  Optional<List<InternalAirport>> getAirportByIata(String iata);

  Optional<List<InternalAirport>> getAirportByCityContainsIgnoreCase(String city);

  // FIXME: Query does not work.
  @Query(
      value =
          "SELECT i FROM InternalAirport i where i.name like ?1 or i.city like ?1 or i.country like ?1 or i.icao like ?1 or i.iata like ?1")
  Optional<List<InternalAirport>> getByUserString(@Param("input") String searchQuery);
}

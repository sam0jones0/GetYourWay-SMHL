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

  @Query(
      value =
          "SELECT i FROM InternalAirport i where UPPER(i.name) like upper(concat('%', ?1, '%')) or upper(i.city) like upper(concat('%', ?1, '%')) or upper(i.country) like upper(concat('%', ?1, '%')) or upper(i.icao) like upper(concat('%', ?1, '%')) or upper(i.iata) like upper(concat('%', ?1, '%'))")
  Optional<List<InternalAirport>> getByUserString(@Param("input") String searchQuery);
}

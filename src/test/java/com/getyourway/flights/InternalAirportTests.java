package com.getyourway.flights;

import com.getyourway.flights.localairportdb.InternalAirport;
import com.getyourway.flights.localairportdb.InternalAirportRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "classpath:airports-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("flighttest")
public class InternalAirportTests {

  @Autowired private InternalAirportRepo airportRepo;

  @Test
  public void testLoadDataForTestClass() {
    assertEquals(5, airportRepo.findAll().size());
  }

  @Test
  public void testGetAirportByIcao() {
    InternalAirport airport =
        Objects.requireNonNull(
                airportRepo.getAirportByIcao("EGLL").stream().findFirst().orElse(null))
            .get(0);

    assertEquals("London Heathrow Airport", airport.getName());
  }

  @Test
  public void getAirportByIata() {
    InternalAirport airport =
        Objects.requireNonNull(
                airportRepo.getAirportByIata("LGW").stream().findFirst().orElse(null))
            .get(0);

    assertEquals("London Gatwick Airport", airport.getName());
  }

  @Test
  public void testGetAirportByCityContainsIgnoreCase() {
    List<InternalAirport> airports =
        Objects.requireNonNull(
            airportRepo.getAirportByCityContainsIgnoreCase("London").stream()
                .findFirst()
                .orElse(null));
    assertEquals(5, airports.size());
  }

  // TODO testGetByUserString
  //  @Test
  //  public void testGetByUserString() {}
}

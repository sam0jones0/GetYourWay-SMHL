package com.getyourway.flights.localairportdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(InternalAirport.class)
@Sql(value = "classpath:airports-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("flighttest")
public class InternalAirportTests {

  @Autowired private InternalAirportRepo airportRepo;

  @Test
  public void testLoadDataForTestClass() {
    assertEquals(5, airportRepo.findAll().size());
  }
}

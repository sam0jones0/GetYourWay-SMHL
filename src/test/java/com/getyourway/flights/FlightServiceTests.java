//package com.getyourway.flights;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.getyourway.flights.localairportdb.InternalAirportRepo;
//import org.junit.Ignore;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.io.InputStream;
//
//@Ignore
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = FlightsService.class)
//@ActiveProfiles("flighttest")
//public class FlightServiceTests {
//
//  @MockBean InternalAirportRepo airportRepo;
//
//  @MockBean WebClient webClient;
//
//  @InjectMocks FlightsService flightsService = new FlightsService();
//
//  private String readJsonFromDiskResource(String filename) {
//    try (InputStream in =
//        Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)) {
//      ObjectMapper mapper = new ObjectMapper();
//      JsonNode jsonNode = mapper.readValue(in, JsonNode.class);
//      return mapper.writeValueAsString(jsonNode);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//
////
////  @Test
////  public void testGetAirportsNearby() {
////    String json = readJsonFromDiskResource("./getAirportsNearbyExternalResponse.json");
////
////    System.out.println(flightsService.getAirportsNearby((float) 50.3, (float) -0.3));
////
//    //    https://wiremock.org/docs/junit-jupiter/
//    //
//    //    AirportResponseDTO response = flightsService.getAirportsNearby((float) 51.4453, (float)
//    // -0.3375);
//    //    System.out.println(response);
//
//    //    mockBackend.enqueue();
//  }

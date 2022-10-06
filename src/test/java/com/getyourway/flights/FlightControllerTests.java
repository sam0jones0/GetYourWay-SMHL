//package com.getyourway.flights;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.squareup.okhttp.mockwebserver.MockResponse;
//import com.squareup.okhttp.mockwebserver.MockWebServer;
//import org.junit.Ignore;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Ignore
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = FlightsController.class)
//@ActiveProfiles("flighttest")
//public class FlightControllerTests {
//
//  public static MockWebServer mockBackend;
//
//  @MockBean FlightsService flightsService;
//  @InjectMocks FlightsController flightsController = new FlightsController();
//
//  @BeforeAll
//  static void setUp() throws IOException {
//    mockBackend = new MockWebServer();
//    mockBackend.start();
//  }
//
//  @AfterAll
//  static void tearDown() throws IOException {
//    mockBackend.shutdown();
//  }
//
//  @BeforeEach
//  void initialise() {
//    String baseUrl =
//        String.format("http://%s:%s", mockBackend.getHostName(), mockBackend.getPort());
//  }
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
//  @Test
//  public void testGetNearbyAirports() {
//    String json = readJsonFromDiskResource("./getAirportsNearbyExternalResponse.json");
//    mockBackend.enqueue(new MockResponse().setBody(json));
//    var a = this.flightsController.getNearbyAirports((float) 50.3, (float) -3.01);
//    System.out.println();
//
//    // https://howtodoinjava.com/java/library/mockwebserver-junit-webclient/
//
//    ResponseEntity<AirportResponseDTO> response =
//        flightsController.getNearbyAirports((float) 51.4453, (float) -0.3375);
//    System.out.println(response);
//
//    //    mockBackend.enqueue();
//  }
//}

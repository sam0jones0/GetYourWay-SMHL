import { useState } from "react";
import "./Trip.css";
import TripSubheader from "./TripSubheader/TripSubheader";
import Map from "./Map/Map";
import { Wrapper, Status } from "@googlemaps/react-wrapper";

export default function Trip() {
  const [userLocation, setUserLocation] = useState([51.5366, 0.0758]);
  const [nearbyAirports, setNearbyAirports] = useState({});
  const [departureAirport, setDepartureAirport] = useState({});
  const [destinationAirport, setDestinationAirport] = useState("Dubrovnik");
  const [tripDate, setTripDate] = useState(new Date());
  
  return (
    <>
      <TripSubheader
        userLocation={userLocation}
        setUserLocation={setUserLocation}
        nearbyAirports={nearbyAirports}
        setNearbyAirports={setNearbyAirports}
        departureAirportProp={departureAirport}
        setDepartureAirport={setDepartureAirport}
        destinationAirportProp={destinationAirport}
        setDestinationAirport={setDestinationAirport}
        tripDateProp={tripDate}
        setTripDate={setTripDate}
      />
      <Wrapper apiKey={process.env.REACT_APP_MAP_API_KEY}>
        <Map 
          location={userLocation}
          departure={{lat: 51.8860, lng: 0.2389}} // London Stanstead Airport
          destination={{lat: 49.0081, lng: 2.5509}} // Paris Charles de Gaulle Airport
        />
      </Wrapper>
    </>
  );
}

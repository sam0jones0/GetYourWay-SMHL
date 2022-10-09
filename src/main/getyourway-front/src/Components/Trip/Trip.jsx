import { useState } from "react";
import "./Trip.css";
import TripSubheader from "./TripSubheader/TripSubheader";
import Dropdowns from "./TripSubheader/Dropdowns/Dropdowns";
import Map from "./Map/Map";
import { Wrapper, Status } from "@googlemaps/react-wrapper";

export default function Trip() {
  const [userLocation, setUserLocation] = useState([]);
  const [nearbyAirports, setNearbyAirports] = useState({});
  const [departureAirport, setDepartureAirport] = useState({
    name: "From Airport",
  });
  const [destinationAirport, setDestinationAirport] = useState({});
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
          departureLocation={departureAirport.location} 
          destination={{ lat: destinationAirport.lat, lng: destinationAirport.lon }} 
          destinationName={destinationAirport.name}
          center={{lat: 51.5072, lng: 0.1276}} // London
        />
      </Wrapper>
    </>
  );
}

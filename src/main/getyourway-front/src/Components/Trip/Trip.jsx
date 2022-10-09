import { useState, createContext } from "react";
import "./Trip.css";
import TripSubheader from "./TripSubheader/TripSubheader";
import Dropdowns from "./TripSubheader/Dropdowns/Dropdowns";
import Map from "./Map/Map";
import { Wrapper, Status } from "@googlemaps/react-wrapper";
import tripContextProvider from "./TripContext";

const tripContext = tripContextProvider;

export default function Trip() {
  const [userLocation, setUserLocation] = useState([0, 0]);
  const [nearbyAirports, setNearbyAirports] = useState({});
  const [departureAirport, setDepartureAirport] = useState({
    name: "From Airport",
  });
  const [destinationAirport, setDestinationAirport] = useState({});
  const [tripDate, setTripDate] = useState(
    new Date().toISOString().slice(0, 10)
  );

  return (
    <tripContext.Provider
      value={{ departureAirport, destinationAirport, tripDate }}
    >
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
    </tripContext.Provider>
  );
}

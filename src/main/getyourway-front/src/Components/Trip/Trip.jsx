import { useState } from "react";
import "./Trip.css";
import TripSubheader from "./TripSubheader/TripSubheader";

export default function Trip() {
  const [userLocation, setUserLocation] = useState({});
  const [nearbyAirports, setNearbyAirports] = useState({});
  const [departureAirport, setDepartureAirport] = useState({});
  const [destinationAirport, setDestinationAirport] = useState({});
  const [tripDate, setTripDate] = useState({});

  return (
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
  );
}

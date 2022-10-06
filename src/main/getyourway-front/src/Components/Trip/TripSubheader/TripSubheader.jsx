import ToAirport from "./TripChildComponents/ToAirport";
import FromAirport from "./TripChildComponents/FromAirport";
import Calendar from "./TripChildComponents/Calendar";

function TripSubheader(props) {
  return (
    <div className="subheaderItems">
      <FromAirport
        nearbyAirports={props.nearbyAirports}
        setNearbyAirports={props.setNearbyAirports}
        userLocation={props.userLocation}
        setUserLocation={props.setUserLocation}
        departureAirportProp={props.departureAirport}
        setDepartureAirport={props.setDepartureAirport}
      />
      <ToAirport
        destinationAirportProp={props.destinationAirport}
        setDestinationAirport={props.setDestinationAirport}
      />
      <Calendar tripDateProp={props.tripDate} setTripDate={props.setTripDate} />
    </div>
  );
}

export default TripSubheader;

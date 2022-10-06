import ToAirport from "./TripChildComponents/ToAirport";
import FromAirport from "./TripChildComponents/FromAirport";
import SelectDate from "./TripChildComponents/SelectDate";

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
      <SelectDate
        tripDateProp={props.tripDate}
        setTripDate={props.setTripDate}
      />
    </div>
  );
}

export default TripSubheader;

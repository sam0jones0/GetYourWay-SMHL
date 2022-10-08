import React, { useEffect } from "react";

import FlightTimes from "./FlightTimes/FlightTimes";
import WeatherForecast from "./WeatherForecast/WeatherForecast";

function Dropdowns(props) {
  return (
    <>
      <div class="mx-3 w-25">
        <FlightTimes
          departureAirportProp={props.departureAirport}
          tripDateProp={props.tripDate}
          destinationAirportProp={props.destinationAirport}
        />
      </div>
      <div class="mx-3 w-25">
        <WeatherForecast />
      </div>
    </>
  );
}

export default Dropdowns;

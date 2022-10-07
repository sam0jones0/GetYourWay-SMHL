import React, { useEffect } from "react";

import FlightTimes from "./FlightTimes/FlightTimes";
import WeatherForecast from "./WeatherForecast/WeatherForecast";

function Dropdowns() {
  return (
    <>
      <div class="mx-3 w-25">
        <FlightTimes />
      </div>
      <div class="mx-3 w-25">
        <WeatherForecast />
      </div>
    </>
  );
}

export default Dropdowns;

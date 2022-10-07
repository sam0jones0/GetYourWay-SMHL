import React, { useEffect } from "react";

import FlightTimes from "./FlightTimes/FlightTimes";
import WeatherForecast from "./WeatherForecast/WeatherForecast";

function Dropdowns() {
  return (
    <>
      <div class="mx-4 w-25">
        <FlightTimes />
      </div>
      <div class="mx-5 w-25">
        <WeatherForecast />
      </div>
    </>
  );
}

export default Dropdowns;

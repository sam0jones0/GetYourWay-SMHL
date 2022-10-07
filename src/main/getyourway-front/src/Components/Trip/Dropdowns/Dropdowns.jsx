import React, { useEffect } from "react";

import FlightTimes from "./DropdownsChildComponents/FlightTimes";
import WeatherForecast from "./DropdownsChildComponents/WeatherForecast";

function Dropdowns() {
  return (
    <>
      <div class="vw-25 px-2">
        <FlightTimes />
      </div>
      <div class="vw-25 px-2">
        <WeatherForecast />
      </div>
    </>
  );
}

export default Dropdowns;

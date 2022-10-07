import React, { useEffect } from "react";

import FlightTimes from "./DropdownsChildComponents/FlightTimes";
import WeatherForecast from "./DropdownsChildComponents/WeatherForecast";

function Dropdowns() {
  return (
    <>
      <div class="w-25">
        <FlightTimes />
      </div>
      <div class="w-25">
        <WeatherForecast />
      </div>
    </>
  );
}

export default Dropdowns;

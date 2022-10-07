import React, { useEffect } from "react";

import DailyWeather from "./DailyWeather/DailyWeather";

function WeatherForecast() {
  return (
    <>
      <div class="collapse" id="weatherCollapse">
        {/* This should be mapped to list of daily weather forecast objects. */}
        {/* <DailyWeather /> */}

        <div class="border-bottom">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
        <div class="border-bottom">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
        <div class="border-bottom">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
      </div>
      <p class="m-0">
        <button
          class="btn btn-primary w-100"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#weatherCollapse"
          aria-expanded="false"
          aria-controls="weatherCollapse"
        >
          Destination Weather
        </button>
      </p>
    </>
  );
}

export default WeatherForecast;

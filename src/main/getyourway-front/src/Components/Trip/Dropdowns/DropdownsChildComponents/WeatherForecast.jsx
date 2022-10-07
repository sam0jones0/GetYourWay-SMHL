import React, { useEffect } from "react";

function WeatherForecast() {
  return (
    <>
      <div class="collapse" id="weatherCollapse">
        <div class="card card-body">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
        <div class="card card-body">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
        <div class="card card-body">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
      </div>
      <p class="m-0">
        <button
          class="btn btn-primary"
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

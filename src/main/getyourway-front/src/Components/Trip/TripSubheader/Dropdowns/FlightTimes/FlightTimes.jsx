import React, { useEffect } from "react";

import Flight from "./Flight/Flight";

function FlightTimes() {
  return (
    <>
      <div class="collapse" id="flightTimesCollapse">
        <Flight />
      </div>
      <p class="m-0">
        <button
          class="btn btn-primary w-100"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#flightTimesCollapse"
          aria-expanded="false"
          aria-controls="flightTimesCollapse"
        >
          Available Flights
        </button>
      </p>
    </>
  );
}

export default FlightTimes;

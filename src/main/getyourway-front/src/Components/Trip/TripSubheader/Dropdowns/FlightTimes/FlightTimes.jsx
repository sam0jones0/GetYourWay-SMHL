import React, { useEffect } from "react";

import Flight from "./Flight/Flight";

function FlightTimes() {
  return (
    <>
      <div class="collapse" id="flightTimesCollapse">
        {/* This should be mapped to list of flight objects. */}
        <Flight />
        <Flight />
        <Flight />
        {/* <div class="border-bottom">
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
        </div> */}
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

import React, { useEffect } from "react";

function FlightTimes() {
  return (
    <>
      <div class="collapse" id="flightTimesCollapse">
        <div class="dropdown-container">
          <div class="cards-container">
            <div class="card card-body">
              Some placeholder content for the collapse component. This panel is
              hidden by default but revealed when the user activates the
              relevant trigger.
            </div>
            <div class="card card-body">
              Some placeholder content for the collapse component. This panel is
              hidden by default but revealed when the user activates the
              relevant trigger.
            </div>
            <div class="card card-body">
              Some placeholder content for the collapse component. This panel is
              hidden by default but revealed when the user activates the
              relevant trigger.
            </div>
          </div>
        </div>
      </div>
      <p class="m-0">
        <button
          class="btn btn-primary"
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

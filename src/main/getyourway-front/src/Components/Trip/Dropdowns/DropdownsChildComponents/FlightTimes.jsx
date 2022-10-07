import React, { useEffect } from "react";

function FlightTimes() {
  return (
    <>
      <div class="dropdown overlay-dropdowns">

        <ul
          class="dropdown-menu overlay-dropdowns"
          aria-labelledby="dropdownMenuButton1"
        >
          <li>
            <a class="dropdown-item overlay-dropdowns" href="#">
              Action
            </a>
          </li>
          <li>
            <a class="dropdown-item overlay-dropdowns" href="#">
              Another action
            </a>
          </li>
          <li>
            <a class="dropdown-item overlay-dropdowns" href="#">
              Something else here
            </a>
          </li>
        </ul>
      </div>
      {/* <div class="dropdown" id="flightsDropdown">
        <button
          class="btn btn-primary dropdown-toggle"
          type="button"
          id="flightsDropdown"
          data-bs-toggle="dropdown"
          data-bs-target="#flightsDropdown"
          aria-expanded="false"
          aria-controls="flightsDropdown"
        >
          Available Flights
        </button>
        <ul
          class="btn btn-secondary dropdown-toggle"
          type="button"
          id="dropdownMenuButton1"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <li class="card card-body">
            Some placeholder content for the collapse component. This panel is
            hidden by default but revealed when the user activates the relevant
            trigger.
          </li>
          <li class="card card-body">
            Some placeholder content for the collapse component. This panel is
            hidden by default but revealed when the user activates the relevant
            trigger.
          </li>
          <li class="card card-body">
            Some placeholder content for the collapse component. This panel is
            hidden by default but revealed when the user activates the relevant
            trigger.
          </li>
        </ul>
      </div> */}
    </>
  );
}

export default FlightTimes;

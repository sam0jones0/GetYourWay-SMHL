import React, { useEffect } from "react";
import axios from "axios";
import Flight from "./Flight/Flight";

// departureAirportProp={props.departureAirport}
// tripDateProp={props.tripDate}
// destinationAirportProp={props.destinationAirport}

const FlightTimes = ({
  departureAirportProp = {
    name: "From Airport",
  },
  tripDateProp = new Date().toISOString().slice(0, 10),
  destinationAirportProp = {},
}) => {
  let availableFlights = {};

  useEffect(() => {
    if (
      departureAirportProp != "From Airport" &&
      Object.keys(destinationAirportProp).length !== 0
    ) {
      let url =
        "http://localhost:8081/api/flights/flightSchedule?" +
        new URLSearchParams({
          depIcao: departureAirportProp.icao,
          arrIcao: destinationAirportProp.icao,
          depDate: tripDateProp,
        });
      let response = axios
        .get(url, {
          headers: {
            method: "GET",
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            mode: "no-cors",
            headers: {
              Accept: "application/json",
            },
          },
        })
        .then((response) => {
          availableFlights = response.data;
          console.log("XHXUSHUHGDSGHDISHIDUDIUHSIUHDSIUHDSIUHDSIUHIUHS");
          console.log(availableFlights);
        });
    }
  }, [departureAirportProp, tripDateProp, destinationAirportProp]);

  return (
    <>
      <div class="collapse" id="flightTimesCollapse">
        <Flight
        // flightNum={flightNum}
        // fromAirport={fromAirport}
        // toAirport={toAirport}
        // departTime={departTime}
        // flightDuration={flightDuration}
        />
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
};

export default FlightTimes;

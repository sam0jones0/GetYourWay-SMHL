import React, { useEffect } from "react";
import axios from "axios";
import Flight from "./Flight/Flight";

function FlightTimes(props) {
  // departureAirportProp={props.departureAirport}
  // tripDateProp={props.tripDate}
  // destinationAirportProp={props.destinationAirport}

  let availableFlights = {};

  useEffect(() => {
    if (
      props.destinationAirport != undefined &&
      props.departureAirport != undefined &&
      props.tripDate != undefined &&
      Object.keys(props.destinationAirport) != 0
    ) {
      let url =
        "http://localhost:8081/api/flights/flightSchedule?" +
        new URLSearchParams({
          depIcao: props.departureAirport.icao,
          arrIcao: props.destinationAirport.icao,
          depDate: props.tripDate,
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
  }, [props.departureAirport, props.tripDate, props.destinationAirport]);

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
}

export default FlightTimes;

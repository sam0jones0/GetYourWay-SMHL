import React, { useEffect, useContext } from "react";
import axios from "axios";
import Flight from "./Flight/Flight";
import tripContextProvider from "../../../TripContext";

const tripContext = tripContextProvider;

function FlightTimes() {
  // departureAirportProp={props.departureAirport}
  // tripDateProp={props.tripDate}
  // destinationAirportProp={props.destinationAirport}
  let { departureAirport, destinationAirport, tripDate } =
    React.useContext(tripContext);

  useEffect(() => {
    if (
      departureAirport != "From Airport" &&
      Object.keys(destinationAirport).length !== 0
    ) {
      let availableFlights = {};
      let url =
        "http://localhost:8081/api/flights/flightSchedule?" +
        new URLSearchParams({
          depIcao: departureAirport.icao,
          arrIcao: destinationAirport.icao,
          depDate: tripDate,
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
  }, [departureAirport, destinationAirport, tripDate]);

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

import React, { useEffect, useContext, useState } from "react";
import axios from "axios";
import Flight from "./Flight/Flight";
import tripContextProvider from "../../../TripContext";

const tripContext = tripContextProvider;
// let availableFlights = [];

function FlightTimes() {
  let [availableFlights, setAvailableFlights] = useState([]);
  let { departureAirport, destinationAirport, tripDate } =
    React.useContext(tripContext);

  useEffect(() => {
    if (
      departureAirport != "From Airport" &&
      Object.keys(destinationAirport).length !== 0
    ) {
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
            cache: "no-cache",
            mode: "no-cors",
            headers: {
              Accept: "application/json",
            },
          },
        })
        .then((response) => {
          setAvailableFlights(response.data);
        });
    }
  }, [departureAirport, destinationAirport, tripDate]);

  useEffect(() => {
    if (availableFlights.length > 0) {
      let flightsDropdown = document.querySelector(".flightsButton");
      flightsDropdown.style.display = "";
    }
  }, [availableFlights]);

  function msToTimeStr(s) {
    var ms = s % 1000;
    s = (s - ms) / 1000;
    var secs = s % 60;
    s = (s - secs) / 60;
    var mins = s % 60;
    var hrs = (s - mins) / 60;

    if (mins === 0) {
      return hrs + "hrs";
    } else {
      return hrs + "hrs " + mins + "mins";
    }
  }

  return (
    <>
      <div class="collapse" id="flightTimesCollapse">
        {availableFlights.map((aFlight) => {
          return (
            <Flight
              flightNum={aFlight.number}
              fromAirport={departureAirport.name}
              toAirport={destinationAirport.name}
              departTime={aFlight.departure.scheduledTimeLocal}
              flightDuration={msToTimeStr(
                new Date(aFlight.arrival.scheduledTimeUtc) -
                  new Date(aFlight.departure.scheduledTimeUtc)
              )}
            />
          );
        })}
      </div>
      <p class="m-0">
        <button
          class="btn btn-primary w-100 flightsButton"
          type="button"
          style={{ display: "none" }}
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

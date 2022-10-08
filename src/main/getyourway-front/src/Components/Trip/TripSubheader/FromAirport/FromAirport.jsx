import axios from "axios";
import React, { useEffect } from "react";
import props from "prop-types";

/**
 * Calls browser API for location. Sets the nearby airports state of parent Trip.
 * Autofills its value to the nearest airport.
 * When clicked, does support free type which would call the internal API
 *  findAirportByText
 */
function FromAirport(props) {
  useEffect(() => {
    const getCoords = async () => {
      const pos = await new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      return {
        lat: Number(pos.coords.latitude).toFixed(5),
        lon: Number(pos.coords.longitude).toFixed(5),
      };
    };

    const findNearbyAirports = async () => {
      const coords = await getCoords();
      props.setUserLocation([coords.lat, coords.lon]);
      // browserGetUserLocation().then(
      let url =
        "https://getyourway.uk:8081/api/flights/nearbyairports?" +
        new URLSearchParams({
          lat: coords.lat,
          lon: coords.lon,
        });
      let response = await axios.get(url, {
        headers: {
          method: "GET",
          cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
          mode: "no-cors",
          headers: {
            Accept: "application/json",
          },
        },
      });
      return response.data;
    };
    findNearbyAirports().then((data) => {
      props.setNearbyAirports(data);

      if (data.items.length > 0) {
        let depArr = data.items[0];
        props.setDepartureAirport(depArr);
        document.querySelector(".fromAirport").placeholder = depArr.name;
      }
    });
  }, []);

  // console.log(props.getNearbyAirports);
  //   {/* <h3>
  //   User location is {props.userLocation[0]},{props.userLocation[1]}{" "}
  // </h3> */}

  return (
    <>
      <div class="form-outline">
        <div class="input-group">
          <span class="input-group-text" id="basic-addon1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-circle-fill"
              viewBox="0 0 16 16"
            >
              <circle cx="8" cy="8" r="8" />
            </svg>
          </span>
          <input
            disabled
            type="text"
            id="formControlLg"
            class="form-control form-control-lg fromAirport"
            placeholder="From Airport"
            aria-label="Departure Airport"
            aria-describedby="basic-addon1"
          />
        </div>
      </div>
    </>
  );
}

export default FromAirport;

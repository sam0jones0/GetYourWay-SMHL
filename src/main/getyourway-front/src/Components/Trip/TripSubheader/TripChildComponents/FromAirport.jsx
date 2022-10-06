import axios from "axios";
import React, { useEffect } from "react";

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
        "http://localhost:8081/api/flights/nearbyairports?" +
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
      // data = data.json();
      // console.log(data);
      props.setNearbyAirports(data);
      console.log(JSON.stringify(data));
    });
  }, []);

  // console.log(props.getNearbyAirports);

  return (
    <h3>
      User location is {props.userLocation[0]},{props.userLocation[1]}{" "}
    </h3>
  );
}

export default FromAirport;

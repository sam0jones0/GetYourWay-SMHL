import React, { useEffect } from "react";

// Calls browser API for location.

// Sets the nearby airports state of parent Trip.

// Autofills its value to the nearest airport.

// When clicked, does support free type which would call the internal API findAirportByText

// nearbyAirports={props.nearbyAirports}
// setNearbyAirports={props.setNearbyAirports}
// userLocation={props.userLocation}
// setUserLocation={props.setUserLocation}
// departureAirportProp={props.departureAirport}
// setDepartureAirport={props.setDepartureAirport}

function FromAirport(props) {
  // const myPromise = new Promise((resolve, reject) => {
  //   setTimeout(() => {
  //     resolve("foo");
  //   }, 300);
  // });

  // function browserGetUserLocation() {
  //   return new Promise((resolve, reject) => {
  //   if ("geolocation" in navigator) {
  //     navigator.geolocation.getCurrentPosition((position) => {
  //       props.setUserLocation([
  //         position.coords.latitude,
  //         position.coords.longitude,
  //         resolve()
  //       ]);
  //     });
  //   }
  // }, 0)}

  useEffect(() => {
    const getCoords = async () => {
      const pos = await new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      return {
        lat: pos.coords.latitude,
        lon: pos.coords.longitude,
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
      let response = await fetch(url, {
        method: "GET",
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        mode: "no-cors",
        headers: {
          "Content-Type": "application/json",
        },
      });
      return await response.json();
    };
    findNearbyAirports().then((data) => {
      // console.log(data);
      props.setNearbyAirports(data);
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

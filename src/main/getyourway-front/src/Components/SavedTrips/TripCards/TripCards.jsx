import axios from "axios";
import React, { useEffect } from "react";

function TripCards() {
  //
  //
  // const findNearbyAirports = async () => {
  //   const coords = await getCoords();
  //   props.setUserLocation([coords.lat, coords.lon]);
  //   // browserGetUserLocation().then(
  //   let url =
  //     "http://localhost:8081/api/flights/nearbyairports?" +
  //     new URLSearchParams({
  //       lat: coords.lat,
  //       lon: coords.lon,
  //     });
  //   let response = await axios.get(url, {
  //     headers: {
  //       method: "GET",
  //       cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
  //       mode: "no-cors",
  //       headers: {
  //         Accept: "application/json",
  //       },
  //     },
  //   });
  //   return response.data;
  // };
  //
  const getSavedTrips = async () => {};
  //
  return (
    <div>
      <div class="card text-left">
        <div class="card-body">
          <h5 class="card-title">Special title treatment</h5>
        </div>
      </div>
      <div class="card-group">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">
              This is a wider card with supporting text below as a natural
              lead-in to additional content. This content is a little bit
              longer.
            </p>
            <p class="card-text">
              <small class="text-muted">Last updated 3 mins ago</small>
            </p>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">
              This card has supporting text below as a natural lead-in to
              additional content.
            </p>
            <p class="card-text">
              <small class="text-muted">Last updated 3 mins ago</small>
            </p>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">
              This is a wider card with supporting text below as a natural
              lead-in to additional content. This card has even longer content
              than the first to show that equal height action.
            </p>
            <p class="card-text">
              <small class="text-muted">Last updated 3 mins ago</small>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default TripCards;

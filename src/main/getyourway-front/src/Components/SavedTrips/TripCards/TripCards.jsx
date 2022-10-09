import React, { useState, useEffect } from "react";
import { isCompositeComponent } from "react-dom/test-utils";
import SavedTrips from "../Index/SavedTrips";
import SingleTripCard from "./SingleTripCard";

function TripCards(props) {
  const [savedTrips, setSavedTrips] = useState([
    {
      tripName: "No Trips :(",
      departureAirport: "No airport :(",
      destinationAirport: "No destination :(",
      departureDate: "No date :(",
    },
  ]);

  useEffect(() => {
    const getUserId = async () => {
      // Make Get request
      let response = await fetch("/api/auth/isUserAuthenticated", {
        method: "GET",
      });
      // Process response
      console.log(response);
      if (response.status == 401) {
        props.setUserId("not Found");
      } else if (response.status == 200) {
        let idHolder = await response.json();
        console.log(idHolder);
        console.log(idHolder.id);
        props.setUserId(idHolder.id);
        console.log(response.id);
        return idHolder.id;
      }
    };

    getUserId().then((id) => {
      const getSavedTrips = async () => {
        try {
          const response = await fetch("/api/users/" + id + "/trips", {
            method: "GET",
          });
          if (response.status == 401) {
            console.log("no trips");
          } else if (response.status == 200) {
            console.log(response);
            let tripHolder = await response.json();
            console.log(tripHolder);
            setSavedTrips(tripHolder);
          }
        } catch (error) {
          console.log(error);
        }
      };
      getSavedTrips();
    });
  }, [props.userId]);

  // let userId = props.userId;

  // useEffect(() => {
  //   getSavedTrips();
  // }, []);

  return (
    <>
      {savedTrips.length !== 0 ? (
        savedTrips.map((aTrip) => {
          return (
            <SingleTripCard
              tripName={aTrip.tripName}
              departureAirport={aTrip.departureAirport}
              destinationAirport={aTrip.destinationAirport}
              departureDate={aTrip.departureDateTime}
            />
          );
        })
      ) : (
        <div class="card text-left py-4">
          <div class="card-body p-4">
            <h5 class="card-title p-2">No Trips found</h5>
          </div>
        </div>
      )}
    </>
  );
}

export default TripCards;

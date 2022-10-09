import Header from "../../Header";
import TripCards from "../TripCards/TripCards";
import { useState } from "react";

function SavedTrips() {
  const [userId, setUserId] = useState();

  return (
    <div>
      <Header />
      <div class="card text-left">
        <div class="card-body">
          <h2 class="card-title">Saved Trips</h2>
        </div>
      </div>
      <TripCards userId={userId} setUserId={setUserId} />
    </div>
  );
}

export default SavedTrips;

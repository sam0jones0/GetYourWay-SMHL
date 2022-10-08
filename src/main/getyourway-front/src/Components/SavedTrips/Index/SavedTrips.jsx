import Header from "../../Header";
import TripCards from "../TripCards/TripCards";

function SavedTrips() {
  return (
    <div>
      <Header />
      <div class="card text-left">
        <div class="card-body">
          <h3 class="card-title">Special title treatment</h3>
        </div>
      </div>
      <div class="card text-left">
        <div class="card-body">
          <h5 class="card-title">Special title treatment</h5>
        </div>
      </div>
      <TripCards />

      <TripCards />
    </div>
  );
}

export default SavedTrips;

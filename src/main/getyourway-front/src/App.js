import logo from "./logo.svg";
import "./App.css";
import { GoogleMap } from "@react-google-maps/api";

import Header from "./Components/Header";

import Trip from "./Components/Trip/Trip.jsx";

function App() {
  return (
    <>
      <div className="App">
        <Header />
        <Trip />
      </div>

      <div className="d-flex">
        <p>
          {" "}
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger. Some placeholder content for the collapse component. This
          panel is hidden by default but revealed when the user activates the
          relevant trigger. Some placeholder content for the collapse component.
          This panel is hidden by default but revealed when the user activates
          the relevant trigger.
        </p>
        <p>
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger. Some placeholder content for the collapse component. This
          panel is hidden by default but revealed when the user activates the
          relevant trigger.
        </p>
      </div>

      {/* <div class="collapse" id="flightTimesCollapse">
        <div class="card card-body">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
        <div class="card card-body">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
        <div class="card card-body">
          Some placeholder content for the collapse component. This panel is
          hidden by default but revealed when the user activates the relevant
          trigger.
        </div>
      </div>
      <p class="m-0">
        <button
          class="btn btn-primary"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#flightTimesCollapse"
          aria-expanded="false"
          aria-controls="flightTimesCollapse"
        >
          Available Flights
        </button>
      </p> */}
    </>
  );
}

export default App;

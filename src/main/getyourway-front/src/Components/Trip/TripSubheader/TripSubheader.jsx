import ToAirport from "./TripChildComponents/ToAirport";
import FromAirport from "./TripChildComponents/FromAirport";
import SelectDate from "./TripChildComponents/SelectDate";
import Dropdowns from "../Dropdowns/Dropdowns";

function TripSubheader(props) {
  return (
    <div class="container-fluid border-top p-0 overflow-hidden">
      <div class="container-fluid p-0">
        <div class="row row-cols-2 d-flex">
          <div class="col-md-6 bg-light border-bottom subheaderItems d-flex align-items-md-center">
            <div class="p-2">
              <FromAirport
                nearbyAirports={props.nearbyAirports}
                setNearbyAirports={props.setNearbyAirports}
                userLocation={props.userLocation}
                setUserLocation={props.setUserLocation}
                departureAirportProp={props.departureAirport}
                setDepartureAirport={props.setDepartureAirport}
              />
            </div>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="34"
              height="34"
              fill="gray"
              class="bi bi-arrow-left-right"
              viewBox="0 0 16 16"
            >
              <path
                fill-rule="evenodd"
                d="M1 11.5a.5.5 0 0 0 .5.5h11.793l-3.147 3.146a.5.5 0 0 0 .708.708l4-4a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 11H1.5a.5.5 0 0 0-.5.5zm14-7a.5.5 0 0 1-.5.5H2.707l3.147 3.146a.5.5 0 1 1-.708.708l-4-4a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 4H14.5a.5.5 0 0 1 .5.5z"
              />
            </svg>
            <div class="p-2">
              <ToAirport
                destinationAirportProp={props.destinationAirport}
                setDestinationAirport={props.setDestinationAirport}
              />
            </div>
            <div class="p-2 ps-2">
              <SelectDate
                tripDateProp={props.tripDate}
                setTripDate={props.setTripDate}
              />
            </div>
          </div>
          <div class="col-md-6 p-2 bg-light border-bottom">
            <p>EMPTY SPACE FOR RIGHT SIDE OF TRIP SUBHEADER</p>
          </div>
          <div class="col-md-6 subheaderItems d-flex align-items-md-center">
            <div class="p-2 d-flex align-items-md-center">
              <Dropdowns />
            </div>
          </div>

          <div class="col-md-6 p-2">
            <p>EMPTY SPACE FOR RIGHT SIDE OF DROPDOWN SUBHEADER</p>
          </div>
        </div>
      </div>

      <div class="row bg-light border-bottom">
        <div className="subheaderItems col-md-6">
          <div class="d-flex flex-row align-items-md-center"></div>
        </div>
      </div>
      <div class="row">
        <div className="col-md-6"></div>
      </div>
    </div>
  );
}

export default TripSubheader;

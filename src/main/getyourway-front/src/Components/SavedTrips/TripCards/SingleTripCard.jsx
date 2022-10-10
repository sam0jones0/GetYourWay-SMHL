function SingleTripCard(props) {
  console.log(props.departureDate);
  // let variable1 = JSON.stringify(props.departureDate);
  // let departureDate = props.departureDate.toLocalDateString();

  let departureDateString = (date) => {
    let deptDate = new Date(date);
    return (
      deptDate.toDateString() + " at " + deptDate.toTimeString().slice(0, 5)
    );
  };

  return (
    <div class=" pb-4">
      <div class="card text-left text-white bg-secondary">
        <div class="card-body">
          <h4 class="card-title">{props.tripName}</h4>
        </div>
      </div>
      <div class="card-group">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Departure Airport</h5>
            <p></p>
            <p class="card-text pb-2">
              Your trip will be departing from {props.departureAirport} on{" "}
              {departureDateString(props.departureDate)}
            </p>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Destination Airport</h5>
            <p></p>
            <p class="card-text">
              Your destination is {props.destinationAirport}
            </p>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Destination Weather Forecast</h5>
            <p class="card-text">
              This is where the weather forecast information will be stored.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SingleTripCard;

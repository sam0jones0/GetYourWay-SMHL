import { useEffect } from "react";

function ToAirport(props) {
  // destinationAirportProp={props.destinationAirport}
  // setDestinationAirport={props.setDestinationAirport}

  return (
    <form>
      <label>
        Destination Airport
        <input
          type="text"
          value={props.destinationAirport}
          onSubmit={(i) =>
            console.log(props.setDestinationAirport(i.target.value))
          }
        />
      </label>
      {console.log(props.destinationAirport)}
    </form>
  );
}

export default ToAirport;

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
          onChange={(i) => props.setDestinationAirport(i.target.value)}
        />
      </label>
    </form>
  );
}

export default ToAirport;

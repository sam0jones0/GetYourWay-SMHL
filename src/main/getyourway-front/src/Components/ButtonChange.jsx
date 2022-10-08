import { useState, useCallback } from "react";
import { Link } from "react-router-dom";
import TripSubheader from "./Trip/TripSubheader/TripSubheader";

function ButtonChange(props) {
  const [buttonIndex, setButtonIndex] = useState(1);

  const mod = (n, m) => ((n % m) + m) % m;

  const buttonName = ["Home", "Trips"];
  const buttonLink = ["/", "/saved"];

  const forwards = useCallback(
    () => setButtonIndex((state) => mod(state + 1, buttonName.length)),
    [setButtonIndex, buttonName]
  );

  //"Saved Trips"
  //"/saved"

  const isLoggedIn = props.authenticated;
  if (!isLoggedIn) {
    return (
      <a class="nav-link col-md-6 " style={{}}>
        {
          <Link to="/signup">
            <button className="btn btn-primary">Sign Up</button>
          </Link>
        }
      </a>
    );
  } else {
    return (
      <a class="nav-link col-md-5 " style={{}}>
        {
          <Link to={buttonLink[buttonIndex]}>
            <button className="btn btn-primary" onClick={forwards}>
              {buttonName[buttonIndex]}
            </button>
          </Link>
        }
      </a>
    );
  }
}
export default ButtonChange;

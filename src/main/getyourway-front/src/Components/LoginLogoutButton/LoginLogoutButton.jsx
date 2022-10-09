//import './LoginLogoutButton.css';
import { getDefaultNormalizer } from "@testing-library/react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function LoginLogoutButton(props) {
  // States
  // const [authenticated, setAuthenticated] = useState(false);
  let navigate = useNavigate();

  // Refresh page after login redirect (hacky)
  const refreshPage = () => {
    navigate(0);
  };

  // Check authentication
  useEffect(() => {
    const checkAuthentication = async () => {
      // Make Get request
      let response = await fetch("/api/auth/isUserAuthenticated", {
        method: "GET",
      });
      // Process response
      if (response.status == 401) {
        props.setAuthenticated(false);
      } else if (response.status == 200) {
        props.setAuthenticated(true);
      }
    };
    checkAuthentication();
  }, [props.authenticated]);

  // Conditional login/logout links
  let authLink;
  if (props.authenticated) {
    authLink = (
      <button className="btn btn-outline-secondary" onClick={handleLogout}>
        Logout
      </button>
    );
  } else {
    authLink = (
      <Link to="/login">
        <button className="btn btn-outline-secondary">Login</button>
      </Link>
    );
  }

  // Logout Event
  function handleLogout(event) {
    event.preventDefault();
    try {
      logoutUser();
      props.setAuthenticated(false);
      navigate("/");
    } catch (error) {
      console.log(error);
    }
  }

  // Logout Get Request
  const logoutUser = async () => {
    // Make Get request
    let response = await fetch("/api/auth/logout", {
      method: "GET",
    });
  };

  return authLink;
}

export default LoginLogoutButton;

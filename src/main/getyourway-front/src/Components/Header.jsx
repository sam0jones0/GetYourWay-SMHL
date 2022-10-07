import React from "react";
import logo from "../Sky.png";
import LoginLogoutButton from "./LoginLogoutButton/LoginLogoutButton";
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <nav class="navbar">
      <div class="container-fluid d-flex align-items-md-center">
        <a class="navbar-brand" href="/">
          <img
            src={logo}
            alt="Logo"
            width="50px"
            height="30px"
            class="d-inline-block align-text-top"
          />
          GetYourWay.com
        </a>
        <div className="m-1 row align-items-md-center">
          <a class="nav-link col-md-6" style={{}}>
            {<Link to="/signup">Sign Up</Link>}
          </a>
          <div className="col-md-6">
            <LoginLogoutButton />
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Header;

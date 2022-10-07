import React from "react";
import logo from "../Sky.png";
import LoginLogoutButton from "./LoginLogoutButton/LoginLogoutButton";
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <nav class="navbar p-3">
      <div class="container-fluid d-flex align-items-md-center ">
        <a class="navbar-brand d-flex" href="/">
          <img
            src={logo}
            alt="Logo"
            width="60px"
            height="36px"
            class="d-inline-block align-text-top"
          />
          <h3 class="ps-2" color="blue">
            GetYourWay.com
          </h3>
        </a>
        <div className="row align-items-md-center me-1">
          <a class="nav-link col-md-6 " style={{}}>
            {
              <Link to="/signup">
                <button className="btn btn-primary">Sign Up</button>
              </Link>
            }
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

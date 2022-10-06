import React from 'react';
import logo from '../Sky.png'

const Header = () => {
  return (
    <nav class="navbar bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          <img
            src={logo}
            alt="Logo"
            width='50px'
            height='30px'
            class="d-inline-block align-text-top"
          />
          GetYourWay.com
        </a>
        <button class="btn btn-dark d-flex">Login</button>
      </div>
    </nav>
  );
}

export default Header;
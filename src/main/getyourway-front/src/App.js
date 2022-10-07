import logo from "./logo.svg";
import "./App.css";
import { GoogleMap } from "@react-google-maps/api";

import Header from "./Components/Header";

import Trip from "./Components/Trip/Trip.jsx";

function App() {
  return (
    <div className="App">
      <Header />
      <Trip />
    </div>
  );
}

export default App;

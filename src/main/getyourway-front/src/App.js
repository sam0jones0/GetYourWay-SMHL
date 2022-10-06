
import logo from "./logo.svg";
import "./App.css";
import { GoogleMap } from "@react-google-maps/api";

import Header from './components/Header'
import Trip from "./components/Trip/Trip.jsx";

function App() {
  return (
    <div className="App">
      <h2>Hello</h2>
      <Trip />
    </div>
  );
}

export default App;

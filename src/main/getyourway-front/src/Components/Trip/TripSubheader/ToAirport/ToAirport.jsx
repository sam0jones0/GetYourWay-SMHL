import * as React from "react";
import { useState, useRef } from "react";

import TextField from "@mui/material/TextField";

import Autocomplete from "@mui/material/Autocomplete";
import axios from "axios";

// Top 100 films as rated by IMDb users. http://www.imdb.com/chart/top
const top100Films = [{ title: "The Shawshank Redemption", year: 1994 }];

function ToAirport() {
  const [options, setOptions] = useState([]);
  const previousController = useRef();

  const getData = (searchTerm) => {
    if (previousController.current) {
      previousController.current.abort();
    }
    const controller = new AbortController();
    const signal = controller.signal;
    previousController.current = controller;
    axios
      .get(
        "http://localhost:8081/api/flights/airportsearch?searchTerm=" +
          searchTerm,
        {
          headers: {
            method: "GET",
            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
            mode: "no-cors",
            headers: {
              Accept: "application/json",
            },
          },
        }
      )
      .then(function (response) {
        return response.data;
      })
      .then(function (myJson) {
        console.log("search term: " + searchTerm + ", results: ", myJson);
        const updatedOptions = myJson.map((p) => {
          return { name: p.name };
        });
        setOptions(updatedOptions);
      });
  };

  const onInputChange = (event, value, reason) => {
    if (value) {
      getData(value);
    } else {
      setOptions([]);
    }
  };

  return (
    <>
      <div class="form-outline">
        <div class="input-group">
          <span class="input-group-text" id="basic-addon1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-geo-alt-fill"
              viewBox="0 0 16 16"
            >
              <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10zm0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6z" />
            </svg>
          </span>
          <Autocomplete
            id="combo-box-demo"
            options={options}
            onInputChange={onInputChange}
            getOptionLabel={(option) => option.name}
            style={{ width: 300 }}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Combo box"
                variant="outlined"
                type="text"
                id="formControlLg"
                class="form-control form-control-lg"
                placeholder="Destination Airport"
                aria-label="Destination Airport"
                aria-describedby="basic-addon1"
              />
            )}
          />
          <input />
        </div>
      </div>
    </>
  );
}

export default ToAirport;

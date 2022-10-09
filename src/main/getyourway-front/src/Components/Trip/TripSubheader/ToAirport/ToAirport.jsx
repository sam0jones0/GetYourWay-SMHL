import * as React from "react";
import { useState, useRef } from "react";

import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import axios from "axios";

function ToAirport(props) {
  const [options, setOptions] = useState([]);
  const previousController = useRef();

  const getData = (searchTerm) => {
    if (previousController.current) {
      previousController.current.abort();
    }
    if (searchTerm.length < 3) {
      return;
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
        setOptions(myJson);
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
            onChange={(event, value) =>
              props.setDestinationAirport({ ...value })
            }
            ListboxProps={{ style: { maxHeight: 400, overflow: "auto" } }}
            disablePortal
            disableClearable
            id="destinationAirportSearch"
            style={{ width: "20vw" }}
            options={options}
            onInputChange={onInputChange}
            getOptionLabel={(option) => option.name}
            renderInput={(params) => (
              <TextField
                {...params}
                type="text"
                id="formControlLg"
                class=""
                placeholder="Destination Airport"
                aria-label="Destination Airport"
                aria-describedby="basic-addon1"
              />
            )}
          />
        </div>
      </div>
    </>
  );
}

export default ToAirport;

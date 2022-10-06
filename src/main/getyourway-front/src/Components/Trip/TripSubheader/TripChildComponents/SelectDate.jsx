import React, { useEffect } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";

function SelectDate(props) {
  // useEffect((props) => {
  //   props.setTripDate(
  //     JSON.stringify(window.localStorage.getItem("props.tripDate"))
  //   );
  // }, []);

  // useEffect(
  //   (props) => {
  //     window.localStorage.setItem("props.tripDate", props.tripDate);
  //   },
  //   [props.tripDate]
  // );

  const onDateChange = (newDate) => {
    props.setTripDate(newDate);
    console.log(newDate);
  };

  return (
    <div>
      <h3>This is where the calendar goes</h3>
      <Calendar
        onChange={onDateChange}
        locale={"en-GB"}
        showNeighboringMonth={false}
        value={props.tripDate}
      />
    </div>
  );
}

export default SelectDate;

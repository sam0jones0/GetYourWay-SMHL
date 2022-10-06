import React from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";

function SelectDate(props) {
  const onDateChange = (newDate) => {
    props.setTripDate(newDate);
    console.log(newDate);
  };
  return (
    <div>
      {/* <Calendar
        onChange={onDateChange}
        locale={"en-GB"}
        showNeighboringMonth={false}
        value={props.tripDate}
      /> */}
    </div>
  );
}

export default SelectDate;

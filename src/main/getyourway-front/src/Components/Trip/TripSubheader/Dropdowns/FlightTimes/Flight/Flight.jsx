import React, { useEffect } from "react";

function Flight({
  flightNum,
  fromAirport,
  toAirport,
  departTime,
  flightDuration,
}) {
  let departureDateString = (date) => {
    let deptDate = new Date(date);
    return (
      deptDate.toDateString() + " at " + deptDate.toTimeString().slice(0, 5)
    );
  };

  return (
    <>
      <div class="fg-container text-start shadow">
        <div class="fg-flightnumber text-start">
          <button
            type="button"
            class="btn btn-sm btn-outline-secondary fw-bold"
          >
            {flightNum}
          </button>
        </div>
        <div class="fg-fromairport fw-bold">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="blue"
            class="bi bi-airplane-fill"
            viewBox="0 0 16 16"
          >
            <path d="M6.428 1.151C6.708.591 7.213 0 8 0s1.292.592 1.572 1.151C9.861 1.73 10 2.431 10 3v3.691l5.17 2.585a1.5 1.5 0 0 1 .83 1.342V12a.5.5 0 0 1-.582.493l-5.507-.918-.375 2.253 1.318 1.318A.5.5 0 0 1 10.5 16h-5a.5.5 0 0 1-.354-.854l1.319-1.318-.376-2.253-5.507.918A.5.5 0 0 1 0 12v-1.382a1.5 1.5 0 0 1 .83-1.342L6 6.691V3c0-.568.14-1.271.428-1.849Z" />
          </svg>
          &nbsp;From {fromAirport}
        </div>
        <div class="fg-toairport fw-bold">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="hotpink"
            class="bi bi-geo-fill"
            viewBox="0 0 16 16"
          >
            <path
              fill-rule="evenodd"
              d="M4 4a4 4 0 1 1 4.5 3.969V13.5a.5.5 0 0 1-1 0V7.97A4 4 0 0 1 4 3.999zm2.493 8.574a.5.5 0 0 1-.411.575c-.712.118-1.28.295-1.655.493a1.319 1.319 0 0 0-.37.265.301.301 0 0 0-.057.09V14l.002.008a.147.147 0 0 0 .016.033.617.617 0 0 0 .145.15c.165.13.435.27.813.395.751.25 1.82.414 3.024.414s2.273-.163 3.024-.414c.378-.126.648-.265.813-.395a.619.619 0 0 0 .146-.15.148.148 0 0 0 .015-.033L12 14v-.004a.301.301 0 0 0-.057-.09 1.318 1.318 0 0 0-.37-.264c-.376-.198-.943-.375-1.655-.493a.5.5 0 1 1 .164-.986c.77.127 1.452.328 1.957.594C12.5 13 13 13.4 13 14c0 .426-.26.752-.544.977-.29.228-.68.413-1.116.558-.878.293-2.059.465-3.34.465-1.281 0-2.462-.172-3.34-.465-.436-.145-.826-.33-1.116-.558C3.26 14.752 3 14.426 3 14c0-.599.5-1 .961-1.243.505-.266 1.187-.467 1.957-.594a.5.5 0 0 1 .575.411z"
            />
          </svg>
          &nbsp;To {toAirport}
        </div>
        <div class="fg-departuretime">{departureDateString(departTime)}</div>
        <div class="flight-duration fw-bold">{flightDuration}</div>
      </div>
    </>
  );
}

export default Flight;

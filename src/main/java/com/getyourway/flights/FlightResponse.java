package com.getyourway.flights;

public class FlightResponse {
    // STUB
}

// Example Api response for:
// https://airlabs.co/api/v9/schedules?api_key=REDACTED&dep_iata=LHR&arr_iata=CMN
// Need to convert to Java object as per ForecastResponse
//
//
// {
//    "request": {
//        "lang": "en",
//        "currency": "GBP",
//        "time": 3,
//        "id": "REDACTED",
//        "server": "l",
//        "host": "airlabs.co",
//        "pid": REDACTED,
//        "key": {
//            "id": 20140,
//            "api_key": "REDACTED",
//            "type": "free",
//            "expired": "2022-10-26T00:00:00.000Z",
//            "registered": "2022-09-23T16:17:36.000Z",
//            "limits_by_hour": 2500,
//            "limits_by_minute": 250,
//            "limits_by_month": 1000,
//            "limits_total": 990
//        },
//        "params": {
//            "dep_time": "2021-07-14 19:53",
//            "dep_iata": "LHR",
//            "arr_iata": "CMN",
//            "lang": "en"
//        },
//        "version": 9,
//        "method": "schedules",
//        "client": {
//            "ip": "REDACTED",
//            "geo": {
//                "country_code": "GB",
//                "country": "United Kingdom",
//                "continent": "Europe",
//                "city": "Slough",
//                "lat": REDACTED,
//                "lng": REDACTED,
//                "timezone": "Europe/London"
//            },
//            "connection": {
//                "type": "cable/dsl",
//                "isp_code": REDACTED,
//                "isp_name": "REDACTED"
//            },
//            "device": {},
//            "agent": {},
//            "karma": {
//                "is_blocked": false,
//                "is_crawler": false,
//                "is_bot": false,
//                "is_friend": false,
//                "is_regular": true
//            }
//        }
//    },
//    "response": [
//        {
//            "airline_iata": "AT",
//            "airline_icao": "RAM",
//            "flight_iata": "AT801",
//            "flight_icao": "RAM801",
//            "flight_number": "801",
//            "dep_iata": "LHR",
//            "dep_icao": "EGLL",
//            "dep_terminal": "4",
//            "dep_gate": "3",
//            "dep_time": "2022-09-23 18:10",
//            "dep_time_utc": "2022-09-23 17:10",
//            "arr_iata": "CMN",
//            "arr_icao": "GMMN",
//            "arr_terminal": "2",
//            "arr_gate": null,
//            "arr_baggage": null,
//            "arr_time": "2022-09-23 21:35",
//            "arr_time_utc": "2022-09-23 20:35",
//            "cs_airline_iata": null,
//            "cs_flight_number": null,
//            "cs_flight_iata": null,
//            "status": "scheduled",
//            "duration": 205,
//            "delayed": null,
//            "dep_delayed": null,
//            "arr_delayed": null,
//            "aircraft_icao": null,
//            "arr_time_ts": 1663965300,
//            "dep_time_ts": 1663953000
//        },
//        {
//            "airline_iata": "BA",
//            "airline_icao": "BAW",
//            "flight_iata": "BA7847",
//            "flight_icao": "BAW7847",
//            "flight_number": "7847",
//            "dep_iata": "LHR",
//            "dep_icao": "EGLL",
//            "dep_terminal": "4",
//            "dep_gate": "3",
//            "dep_time": "2022-09-23 18:10",
//            "dep_time_utc": "2022-09-23 17:10",
//            "arr_iata": "CMN",
//            "arr_icao": "GMMN",
//            "arr_terminal": "2",
//            "arr_gate": null,
//            "arr_baggage": null,
//            "arr_time": "2022-09-23 21:35",
//            "arr_time_utc": "2022-09-23 20:35",
//            "cs_airline_iata": "AT",
//            "cs_flight_number": "801",
//            "cs_flight_iata": "AT801",
//            "status": "scheduled",
//            "duration": 205,
//            "delayed": null,
//            "dep_delayed": null,
//            "arr_delayed": null,
//            "aircraft_icao": null,
//            "arr_time_ts": 1663965300,
//            "dep_time_ts": 1663953000
//        }
//    ],
//    "terms": "Unauthorized access is prohibited and punishable by law. \nReselling data 'As Is' without AirLabs.Co permission is strictly prohibited. \nFull terms on https://airlabs.co/. \nContact us info@airlabs.co"
//}
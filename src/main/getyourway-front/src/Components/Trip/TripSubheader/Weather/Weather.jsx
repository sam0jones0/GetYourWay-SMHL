import { useState, useEffect } from "react";

function Weather(props) {

    const [temp, setTemp] = useState(null);
    const [location, setLocation] = useState(null);

    // Get Weather
    useEffect(() => {
        const getWeather = async () => {
        // Make Get request
        let response = await fetch(`/api/weather/forecast?lat=${props.destination.lat}&lon=${props.destination.lon}`, {
            method: "GET",
        });
        // Process response
        if (response.status == 200) {
            let weather = await response.json();
            console.log("MY RESULT: " + weather);
            setTemp(weather.daily[0].temp.day);
            setLocation(props.destination.name);
        } 
        };
        getWeather();
    }, [props.destination]);
     
    return (temp != null)? (
        <div class="d-flex m-2">
            <div>
            <h2 class="display-8"><strong>{temp}°C</strong></h2>
            <p class="text-muted mb-0">{location}</p>
            </div>
        </div>
    ): <></>
}

export default Weather;
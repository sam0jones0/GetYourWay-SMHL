import './Map.css';
import { GoogleMap, useJsApiLoader, DirectionsService, DirectionsRenderer, Polyline, Marker} from '@react-google-maps/api';
import { useState, useCallback, useRef, useEffect } from 'react';


function Map(props) {

    const [map, setMap] = useState(null)
    const [response, setResponse] = useState(null);
    const [origin, setOrigin] = useState(new window.google.maps.LatLng(props.departure));
    const [destination, setDestination] = useState(new window.google.maps.LatLng(props.destination));

    // --------------- Map Rendering ---------------
    const containerStyle = {
        width: '600px',
        height: '600px'
    };
    
    const center = {
        lat: props.location[0], // lattitude
        lng: props.location[1] //longitude
    };

    const { isLoaded } = useJsApiLoader({
        id: 'google-map-script',
        //googleMapsApiKey: ""
    })

    const onLoad = useCallback(function callback(map) {
        map.setZoom(5)
        setMap(map)
    }, [])

    const onUnmount = useCallback(function callback(map) {
        setMap(null)
     }, [])

    // Polyline
    const onLoadPolyline = polyline => {
        console.log('polyline: ', polyline)
    };

    // Destination Airpot Marker
    const onLoadMarker = marker => {
        console.log('marker: ', marker)
    }

    // Destination Infro Box
    const onLoadInfoBox = infoBox => {
        console.log('infoBox: ', infoBox)
    };

    // -------------- End Map rendering ---------------

    // Directions Callback. Calls the Destination Service

    function directionsCallback(response) {
        console.log(response);

        if (response !== null) {
            if (response.status === 'OK') {
                setResponse(response);
            } else {
                console.log('response: ', response);
            }
        }
    }

    // Airport Polyline
    const path = [
        props.departure,
        props.destination
    ]

    // Airpoty Polyline Options
    const options = {
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: '#FF0000',
        fillOpacity: 0.35,
        clickable: false,
        draggable: false,
        editable: false,
        visible: true,
        paths: [
          origin,
          destination
        ]
      };

  
     return isLoaded ? (
        <div className="container customMap">
            <GoogleMap
                mapContainerStyle={containerStyle}
                center={center}
                onLoad={onLoad}
                onUnmount={onUnmount}
            >
            { (origin != null && destination != null && response == null) && (
                <DirectionsService
                    options={{
                        destination: origin, // origin airport
                        origin: new window.google.maps.LatLng(center), // user location
                        travelMode: "TRANSIT",
                    }}
                    callback={directionsCallback}
                />
            )
            }
            { response != null && (
                 
                <DirectionsRenderer
                    options={{
                        directions: response,
                        preserveViewport: true
                    }}
                />

            )
            }
            { (origin != null && destination != null) &&  (
                
                <Polyline
                    onLoad={onLoadPolyline}
                    path={path}
                    options={options}
                />
                )
            }
            { (origin != null && destination != null) &&  (
                
                <Marker
                    onLoad={onLoadMarker}
                    position={destination}
                    label="C"
                />
                )
            }
            <></>
            </GoogleMap>
        </div>
    ) : <></>
}

export default Map;
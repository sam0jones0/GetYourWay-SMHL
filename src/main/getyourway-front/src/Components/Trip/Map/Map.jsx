import './Map.css';
import { GoogleMap, LoadScript, useJsApiLoader, DirectionsService, DirectionsRenderer, Polyline, Marker, useGoogleMap, useLoadScript, InfoWindow} from '@react-google-maps/api';
import { useState, useCallback, useRef, useEffect } from 'react';
import { json } from 'react-router-dom';


function Map(props) {

    const [map, setMap] = useState(null)
    const [response, setResponse] = useState(null);
    const [origin, setOrigin] = useState({});
    const [destination, setDestination] = useState(new window.google.maps.LatLng(props.destination));

    // Update departure airpot (origin) when use location is obtained
    useEffect(() => {
        console.log("This effect fired");
        const location = props.departureLocation;
        if (location != null) {
            setOrigin({lat: location.lat, lng: location.lon});
        }
        
    },[props.departureLocation])

    // --------------- Map Rendering ---------------
    const containerStyle = {
        width: '100%',
        height: '600px'
    };

    const onLoad = useCallback(function callback(map) {
        map.setZoom(5);
        map.setCenter(props.center);
        setMap(map);
    }, [])

    const onUnmount = useCallback(function callback(map) {
        setMap(null)
    }, [])

    const mapStyles = [
        {
            "featureType": "landscape",
            "elementType": "geometry",
            "stylers": [
              {
                "color": "#f0f0f0"
              },
              {
                "lightness": "0"
              }
            ]
        },
        {
            "featureType": "water",
            "stylers": [
              {
                "color": "#cae0fa"
              }
            ]
        },
        {
            "featureType": "road",
            "stylers": [
              {
                "color": "#ffffff"
              }
            ]
        }
    ];

    // Polyline
    const onLoadPolyline = polyline => {
        console.log('polyline: ', polyline)
    };

    // Destination Airpot Marker
    const onLoadMarker = marker => {
        console.log('marker: ', marker)
    }

    // Destination Info Window
    const onLoadInfo = infoWindow => {
        console.log('infoWindow: ', infoWindow)
    }

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
        origin,
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
          props.destination
        ]
    };

    // InfoWindow Styling
    const divStyle = {
        background: `white`,
        border: `1px solid #ccc`,
        padding: 5,
        width: '100px',
        height: '30px'
    }
    
    // Return
    return (
        <div className=".container-fluid customMap">
            <GoogleMap
                onLoad={onLoad}
                onUnmount={onUnmount}
                mapContainerStyle={containerStyle}
                options={{
                    styles: mapStyles
                }}
            >
            { ( (origin.lat != null) && (origin.lng !=null) && destination != null && response == null) && (
                <DirectionsService
                    options={{
                        origin: new window.google.maps.LatLng(props.center), // user location
                        destination: new window.google.maps.LatLng(origin), // origin airport
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
                    position={props.destination}
                    label={{text: "C", color: "white", borderColor: "white"}}
                    title="Some title"
                >
                    <InfoWindow
                        onLoad={onLoadInfo}
                    >
                        <div style={divStyle}>
                            <p>{props.destinationName}</p>
                        </div>
                    </InfoWindow>

                </Marker>
                
            )}


            <></>
            </GoogleMap>
        </div>
    ) 
}

export default Map;
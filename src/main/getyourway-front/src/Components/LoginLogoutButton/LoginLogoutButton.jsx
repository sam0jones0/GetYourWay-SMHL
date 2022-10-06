//import './LoginLogoutButton.css';
import { getDefaultNormalizer } from '@testing-library/react';
import { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { useNavigate } from 'react-router-dom';

function LoginLogoutButton() {

    // States
    const [authenticated, setAuthenticated] = useState(false);
    let navigate = useNavigate();

    // Refresh page after login redirect (hacky)
    const refreshPage = () => {
        navigate(0);
    }

    // Check authentication 
    useEffect(() => {
        const checkAuthentication = async () => {
            // Make Get request
            let response = await fetch('/api/auth/isUserAuthenticated', {
                method: "GET"
            });
            // Process response
            if (response.status == 401) { 
                setAuthenticated(false);
            } else if(response.status == 200) {
                setAuthenticated(true);
            }
        }
        checkAuthentication();

    }, [authenticated]);

    // Conditional login/logout links
    let authLink;
    if (authenticated) {
        authLink = <button className="btn btn-primary" onClick={handleLogout}>Logout</button>
    } else {
        authLink =  <Link to="/login">
                        <button className="btn btn-primary">
                            Login
                        </button>
                    </Link>
   
    }

    // Logout Event
    function handleLogout(event) {
        event.preventDefault();
        try {
            logoutUser();
            setAuthenticated(false);
            navigate('/');
        } catch(error) {
            console.log(error);
        }
    }

    // Logout Get Request
    const logoutUser = async () => {

        // Make Get request
        let response = await fetch('/api/auth/logout', {
            method: "GET"
        });
    }


    return (
        authLink
    )

    // Make a get call

    // Make get call once per render
    // Set state to true of false

    // If user is authed, change to a logout button (and render profile)
    // If user is not authed, change to login button

    // Login button -> onclick navigate to login
    // Logout button -> onClick, process logout
}

export default LoginLogoutButton;
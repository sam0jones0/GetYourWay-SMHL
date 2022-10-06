import './Form.css';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Form() {

    // States
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [usernameError, setUsernameError] = useState(null);
    const [passwordError, setPasswordError] = useState(null);
    let navigate = useNavigate();

    // On Submit button
    function handleSubmit(event){
        event.preventDefault();
        setPasswordError(null);
        setUsernameError(null);
        try {
            authenticateUser();
        } catch (error) {
            console.log(error); 
        }
    }
    
    // Post function
    const authenticateUser = async () => {
        
        // Make Post request
        let response = await fetch(`/api/auth/login?username=${username}&password=${password}`, {
            method: "POST",
            headers: {
                "Accept" : "application/json",
                "Content-Type" : "application/json"
            }
        });

        // To Json
        let responseJson = await response.json();
        console.log(response);

        // Process response
        if (response.status == 200) { // OK response
                setUsername(""); 
                setPassword("");
                navigate('/');
        } else if(response.status == 404)  { //User not found
            setUsernameError(responseJson.message);
        }  else if(response.status == 401)  { //Incorrect password
            setPasswordError(responseJson.message);
        }
        
            
    }
    
    // Return
    return (
        <form className="p-2" onSubmit={handleSubmit}>
            {/* Username */}
            <div className="form-outline mb-4">
                <input type="text" className="form-control" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} required/>
                {usernameError != null &&
                    <p className="formError">{usernameError}</p>
                }
            </div>

            {/* Password */}
            <div className="form-outline mb-4">
                <input type="password" className="form-control" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required/>
                {passwordError != null &&
                    <p className="formError">{passwordError}</p>
                }
            </div>

            <div className="row mb-4">
                <div className="col-md-6 d-flex justify-content-center">
                    {/* Remember Checkbox */}
                    <div className="form-check mb-3 mb-md-0">
                        <input className="form-check-input" type="checkbox" value="" checked />
                        <label className="form-check-label"> Remember me </label>
                    </div>
                </div>

                <div className="col-md-6 d-flex justify-content-center">
                {/* Forgot Link */}
                <a href="#!">Forgot password?</a>
                </div>
            </div>

            {/* Submit button */}
            <button type="submit" className="btn btn-primary mb-4 text-center">Login</button>
            
            {/*} Register buttons */}
            <div className="text-center">
                <p>Not a member? <a href="#!">Sign Up!</a></p>
            </div>

        </form>
    )
}

export default Form;
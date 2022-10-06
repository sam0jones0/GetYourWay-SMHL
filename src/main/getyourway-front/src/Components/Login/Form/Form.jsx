import './Form.css';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Form() {

    // States
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    let navigate = useNavigate();

    // On Submit button
    function handleSubmit(event){
        event.preventDefault();
        try {
            authenticateUser();
            navigate('/');
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

        // Process response
        if (response.status === 200) { // OK response
                setUsername(""); 
                setPassword("");
        }; //Handle error messages from login here
        //One for username not found 
        //And one for password incorrect
        
            
    }
    
    

    // Return
    return (
        <form class="p-2" onSubmit={handleSubmit}>
            {/* Username */}
            <div class="form-outline mb-4">
                <input type="text" class="form-control" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)}/>
            </div>

            {/* Password */}
            <div class="form-outline mb-4">
                <input type="password" class="form-control" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
            </div>

            <div class="row mb-4">
                <div class="col-md-6 d-flex justify-content-center">
                {/* Remember Checkbox */}
                <div class="form-check mb-3 mb-md-0">
                    <input class="form-check-input" type="checkbox" value="" checked />
                    <label class="form-check-label" for="loginCheck"> Remember me </label>
                </div>
                </div>

                <div class="col-md-6 d-flex justify-content-center">
                {/* Forgot Link */}
                <a href="#!">Forgot password?</a>
                </div>
            </div>

            {/* Submit button */}
            <button type="submit" class="btn btn-primary mb-4 text-center">Login</button>
            
            {/*} Register buttons */}
            <div class="text-center">
                <p>Not a member? <a href="#!">Sign Up!</a></p>
            </div>

        </form>
    )
}

export default Form;
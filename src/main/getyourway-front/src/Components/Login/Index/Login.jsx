import './Login.css';
import Form from '../Form/Form';
import Header from '../../Header'

function Login() {
    
    return (
        <>
            <Header />
            <div class="container">
            
                <div class="card login-card p-2 mt-5 text-center">
                    <h3>Login</h3>
                    <Form />
                    
                </div>

            </div>
        </>
    )
}

export default Login;
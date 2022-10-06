import './Signup.css';
import Form from '../Form/Form';
import SkyLogoWhite from '../../../SkyLogoWhite.png'
import Header from '../../Header'

function Signup() {
    
    return (
        <>
            <Header />
            <div class="container">
            
                <div className="card signup-card p-2 mt-5 text-center">
                    <div className="row">
                        <div className="col-md-5 d-flex align-items-center">
                            <img className="card-img-top" src={SkyLogoWhite} alt="Sky logo"></img>
                        </div>
                        <div className="col-md-7 form-side">
                            <div className="card-body">
                                <h3>Signup</h3>
                                <Form />
                            </div>
                        </div>
                    </div>
                    
                </div>

            </div>
        </>
    )
}

export default Signup;
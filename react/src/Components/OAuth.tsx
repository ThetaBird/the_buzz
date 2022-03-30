import * as React from 'react'
//import ReactDOM from 'react-dom';
import { GoogleLogin } from 'react-google-login';
const axios = require('axios');

type OAuthProps = {
    updateData:any
}
export class Login extends React.Component<OAuthProps>{
    responseGoogle = (response) => {
        this.attemptLogin(response.wc.id_token);
    }
    
    attemptLogin(token){
        const params = {
            id_token: token
        }

        axios.post('https://cse216-group4-test.herokuapp.com/api/auth', params)
          .then((result) => {
            result = result.data.mData;
            console.log(result);
            let updateData = this.props.updateData;
            let data = {
                userToken:token,
                userName:result.name,
                userAvatar:result.pictureUrl,
                userEmail:result.email
            }
            updateData(data);

            localStorage.setItem("user",JSON.stringify(data));
          });
    }
    
    render(){
        return(
            <div id="loginContainer" className='container-fluid text-center'>
                <div id="loginHeader"className='spartan h1'>The Buzz</div>
                <GoogleLogin
                    clientId="841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com"
                    render={renderProps => (
                        <button id="loginButton" type="button" className="btn btn-light px-5 py-2 spartan" onClick={renderProps.onClick} disabled={renderProps.disabled}>Login</button>
                    )}
                    buttonText="Login"
                    onSuccess={this.responseGoogle}
                    onFailure={this.responseGoogle}
                    cookiePolicy={'single_host_origin'}
                />
            </div>
        )
    }
}
export class Logout extends React.Component<OAuthProps>{
    attemptLogout = ()=> {
        let data = {
            userToken:"",
            userName:"",
            userAvatar:"",
            userEmail:""
        }
        let updateData = this.props.updateData;
       updateData(data);
       localStorage.setItem("user","");
    }
    render(){
        return (
            <button id="logoutButton" onClick={this.attemptLogout} type="button" className="btn btn-secondary shadow">Logout</button>
        )
        
    }
}
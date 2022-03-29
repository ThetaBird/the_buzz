import * as React from 'react'
//import ReactDOM from 'react-dom';
import { GoogleLogin } from 'react-google-login';
const axios = require('axios');

type LoginProps = {
    updateData:any
}
export class GoogleOAuth extends React.Component<LoginProps>{
    responseGoogle = (response) => {
        this.attemptInteraction(response.wc.id_token);
    }
    attemptInteraction(token){
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
            }
            updateData(data);
          });
    }
    render(){
        return(
        <GoogleLogin
            clientId="841253943983-23js8dkv8houcvggnt3trl09v83270am.apps.googleusercontent.com"
            buttonText="Login"
            onSuccess={this.responseGoogle}
            onFailure={this.responseGoogle}
            cookiePolicy={'single_host_origin'}
          />)
    }
}
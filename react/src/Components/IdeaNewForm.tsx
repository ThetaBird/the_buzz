import * as React from 'react'
import { Redirect } from "react-router-dom";
const axios = require('axios');

export class IdeaNewForm extends React.Component{
    state={
        subject:"",
        content:"",
        exitForm:false
    }
    onChange = (e) => {
        this.state[e.target.name] = e.target.value;
        console.log(this.state)
    }
    onSubmit = (e) => {
        e.preventDefault();
        /**
         * The following variables are temporary placeholders and should be updated to reflect the actual values.
         * Especially the user, which should probably be passed as the access token for backend to handle.
         */
        const params = {
            subject: this.state.subject,
            content: this.state.content,
            userId: 1,
            attachment: null,
            allowedRoles: null
        }
        console.log(params);

        axios.post('https://cse216-group4-test.herokuapp.com/api/ideas', params)
          .then((result) => {
            console.log(result);
          });
        this.closeForm(null);
      }
    closeForm = (e) => {
        this.state.exitForm=true;
        this.setState(this.state); //actually updates the state, forces a re-render 
    }

    render(){
        console.log("Rendered!")
        if(this.state.exitForm){
         return <Redirect to={'/ideas'}/> 
        }
        return (
        <div id="ideaForm" className='position-fixed d-flex'>
            <div id="ideaFormBox" className="w-50 h-75 mx-auto form-row">
                <div id = "ideaFormHeader" className='container-fluid p-3  mb-2'>New Idea</div>
                <div id = "ideaFormSubject" className='col-auto mx-4'>
                    <label className='mx-2 mt-3'>Subject</label>
                    <small className="form-text text-muted mx-2">Limit: 64 Characters</small>
                    <input onChange={this.onChange} type="text" name="subject" className="form-control form-control-lg required"></input>
                </div>  
                <div id = "ideaFormContent" className='col-auto m-4'>
                    <label className='mx-2'>Content</label>
                    <small className="form-text text-muted mx-2">Limit: 500 Characters</small>
                    <textarea onChange={this.onChange} name="content" className="form-control textarea" rows={9}></textarea>
                </div>
                <div className='col-auto m-4'>
                    <button onClick={this.onSubmit} type="button" className="btn btn-primary">Post Idea</button>
                </div>  
            </div>
        </div>
    );}
}
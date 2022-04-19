import * as React from 'react'
import { Redirect } from "react-router-dom";
const axios = require('axios');

type NewFormProps = {
    user:any,
}
export class IdeaNewForm extends React.Component<NewFormProps>{
    state={
        subject:"",
        content:"",
        exitForm:false,
        user:{
            userToken:"",
            userName:"",
            userId:""
        }
    }
    onChange = (e) => {
        this.state[e.target.name] = e.target.value;
    }
    componentDidMount(){
       this.state.user = this.props.user;
        this.setState(this.state);

        console.log("IdeaNewForm Mount");
        console.log(this.state);
    }
    submitForm = (e) => {
        e.preventDefault();
        console.log("Submitting form");
        console.log(this.state);
        const params = {
            replyTo:0,
            subject: this.state.subject,
            content: this.state.content,
            attachment: null,
            allowedRoles: null
        }
        axios.post(`https://cse216-group4-test.herokuapp.com/api/ideas?token=${this.state.user.userToken}`, params)
          .then((result) => {
            console.log(result);
            this.closeForm();
          });
        
      }
    closeForm = () => {
        this.state.exitForm=true;
        this.setState(this.state); //actually updates the state, forces a re-render 
    }
    render(){
        if(this.state.exitForm){
            return <Redirect to={'/ideas'}/> 
        }
        return (
        <div id="ideaForm" className=''>
            <div id="ideaFormBox" className="w-50 h-50 mx-auto form-row">
                <div id = "ideaFormHeader" className='container-fluid p-3  mb-2'>New Idea</div>
                <div id = "ideaFormSubject" className='col-auto mx-4'>
                    <label className='mx-2 mt-3'>Subject</label>
                    <small className="form-text text-muted mx-2">Limit: 64 Characters</small>
                    <input onChange={this.onChange} type="text" name="subject" className="form-control form-control-lg required shadow-sm"></input>
                </div>  
                <div id = "ideaFormContent" className='col-auto m-4'>
                    <label className='mx-2'>Content</label>
                    <small className="form-text text-muted mx-2">Limit: 500 Characters</small>
                    <textarea onChange={this.onChange} name="content" className="form-control textarea shadow-sm" rows={9}></textarea>
                </div>
                <div>
                <label for="avatar">Choose a profile picture:</label>
                <input type="file" id="avatar" name="avatar" accept="image/png, image/jpeg , pdf"></input>
                </div>
                <div className='row ms-3 mt-4'>
                    <div className='col-auto'>
                        <button onClick={this.submitForm} type="button" className="btn btn-primary px-3 shadow">Post Idea</button>
                    </div>  
                    <div className='col-auto'>
                        <button onClick={this.closeForm} type="button" className="btn btn-secondary px-2 shadow">Cancel</button>
                    </div>  
                </div>
                
            </div>
        </div>
    );}
}
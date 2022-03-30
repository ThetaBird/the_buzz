import * as React from 'react';
import { HashRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
const axios = require('axios');
import { IdeaReactions } from './IdeaReactions';
import {Comment} from './Comment';

type IdeaSpecificProps = {
    ideaId:string,
    user:any,
    refreshList:any
}
export class IdeaSpecific extends React.Component<IdeaSpecificProps>{
    state = {
        idea:{
            ideaId:"",
            userId:"",
            timestamp:"",
            subject:"",
            content:"",
            comments:[],
            editable:false
        },
        closeContainer:false,
        user:{
            userToken:"",
            userName:"",
            userId:"",
            userEmail:""
        },
        commentInput:"",
        submittedReaction:false
    }
    componentDidMount(){
        this.state.user = this.props.user;
        this.setState(this.state);
        console.log("IdeaSpecific Mount");
        console.log(this.state);
        this.pullData();
    }
    pullData(){
        console.log("Pulling specific idea data...")
        console.log(this.props.ideaId)
        axios.get(`https://cse216-group4-test.herokuapp.com/api/idea/${this.props.ideaId}?token=${this.state.user.userToken}`)
        .then((res) => {
            const idea = res.data.mData;
            const comments = idea.comments;
            comments.reverse();
            if(idea.userId == this.state.user.userEmail){idea.editable=true;}
            this.state.idea = idea;
            
            console.log(idea);
            this.setState(this.state);
        });
    }
    closeContainer = () =>{
        this.state.closeContainer = true;
        this.setState(this.state);
    }
    addReaction = (type) =>{
        const params = {type}
        axios.post(`https://cse216-group4-test.herokuapp.com/api/idea/${this.state.idea.ideaId}/reactions?token=${this.state.user.userToken}`, params)
          .then((result) => {
            console.log(result);
            this.state.submittedReaction = true;
            this.setState(this.state);
            //this.closeForm();
          });
    }
    onChange = (e) => {
        this.state[e.target.name] = e.target.value;
    }
    submitComment = (e) => {
        e.preventDefault();
        console.log("Submitting form");
        console.log(this.state);
        const params = {
            replyTo:this.state.idea.ideaId,
            subject: "",
            content: this.state.commentInput,
            attachment: null,
            allowedRoles: null
        }
        axios.post(`https://cse216-group4-test.herokuapp.com/api/ideas?token=${this.state.user.userToken}`, params)
          .then((result) => {
            console.log(result);
           
          });
        
      }
    render(){
        console.log("Rendered Specific Idea")
        console.log(this.state.idea);
        if(this.state.closeContainer || this.props.ideaId == ""){
            return <Redirect to={'/ideas'}/> 
        }
        if(this.state.submittedReaction){
            this.state.submittedReaction=false;
            return <Redirect to={`/ideas/${this.props.ideaId}`}/> 
        }
        let s = this.state.idea.editable;
        const editOptions = () => {
            if(s){
                console.log("editable")
                return <div className='col-1'>editable</div>
            }else{console.log("hey");return <div/>;}
        }
        return(
            <span id = "specificColContainer" className='spartan col-4'>
                <div id="viewIdeaDescription" className='container-fluid position-fixed'>
                    <button onClick={this.closeContainer} type="button" className="btn btn-light px-3">&#60;</button>
                    <span className='mx-4'>Viewing</span> 
                </div>
                <div id="specificCol">
                    <div id="specificIdeaContainer" className='row'>
                        <div className="row h6 text-start">
                            <div className="col-10">{this.state.idea.userId}</div>
                            {editOptions()}
                        </div>
                        <div>
                        <button onClick={() => this.addReaction(1)} type="button" className="btn btn-light px-3">^</button>
                        <button onClick={() => this.addReaction(-1)} type="button" className="btn btn-light px-3">v</button>
                        </div>
                        <div className='row ms-1 h2 text-start'>{this.state.idea.subject}</div>
                        <div className='row ms-1 h5 text-start'>{this.state.idea.content}</div>
                    </div>
                    <div id = "commentsHeader">Comments</div>
                    <div id = "commentsContainer" className='row'>        
                        {this.state.idea.comments.map((ideaData) => <Comment key={ideaData.timestamp} data={ideaData}/>)}
                    </div>
                </div>
                <div id="commentForm" className='row'>
                    <div id = "ideaFormContent" className='col m-4'>
                        <label className='mx-2'>
                            <button onClick={this.submitComment} type="button" className="btn btn-primary px-3 shadow">Comment</button>
                        </label>
                        <small className="form-text text-muted mx-2">Limit: 500 Characters</small>
                        <textarea onChange={this.onChange} name="commentInput" className="form-control textarea shadow-sm" rows={5}></textarea>
                    </div>
                    
                </div>
            </span>
        );
    }
}

import * as React from 'react';
import { HashRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
const axios = require('axios');
import {Comment} from './Comment';

type IdeaSpecificProps = {
    ideaId:string,
    user:any
}
export class IdeaSpecific extends React.Component<IdeaSpecificProps>{
    state = {
        idea:{
            ideaId:"",
            userId:"",
            timestamp:"",
            subject:"",
            content:""
        },
        closeContainer:false,
        user:{
            userToken:"",
            userName:"",
            userId:""
        }
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
        axios.get(`https://cse216-group4-test.herokuapp.com/api/idea/1648448700699?token=${this.state.user.userToken}`)
        .then((res) => {
            const idea = res.data.mData;
            //comments.reverse();
            this.state.idea = idea;
            console.log(idea.userId);
            this.setState({idea});
        });
    }
    closeContainer = () =>{
        this.state.closeContainer = true;
        this.setState(this.state);
    }
    render(){
        console.log("Rendered Specific Idea")
        console.log(this.state.idea);
        if(this.state.closeContainer){
            return <Redirect to={'/ideas'}/> 
        }
        return(
            <span id = "specificColContainer" className='spartan col-3'>
                <div id="specificCol">
                    <div id="viewIdeaDescription" className='container-fluid position-fixed'>
                        <button onClick={this.closeContainer} type="button" className="btn btn-light px-3">&#60;</button>
                       <span className='mx-4'>Viewing</span> 
                    </div>
                    <div id="specificIdeaContainer">
                        <div className="row h6 text-start">
                            <div className="col-sm-1">@User{this.state.idea.userId}</div>
                        </div>
                        <div className='row ms-1 h2 text-start'>{this.state.idea.subject}</div>
                        <div className='row ms-1 h5 text-start'>{this.state.idea.content}</div>
                    </div>
                </div>
            </span>
        );
    }
}
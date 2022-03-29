import * as React from 'react';
import { HashRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
const axios = require('axios');
import {Idea} from './Idea';
import {IdeaNewForm} from './IdeaNewForm';
import {IdeaSpecific} from './IdeaSpecific';

type IdeaListProps = {
    user:any
}

export class IdeaList extends React.Component<IdeaListProps>{
    state = {
        user:{
            userToken:"",
            userName:"",
            userId:""
        },
        ideas:[],
        newForm:false,
        showIdea:false,
        ideaId:""
    };
    
    
    componentWillReceiveProps(nextProps) {
        if(nextProps){
            this.pullData();
        }
    }
    
    componentDidMount(){
    
        this.state.user = this.props.user;
        this.setState(this.state);
        console.log("IdeaList Mount");
        console.log(this.state);
        this.pullData();
    }
    pullData(){
        //this.state.data = Store.getState();
        //this.setState(this.state);

        console.log("Pulling idea data...")
        axios.get(`https://cse216-group4-test.herokuapp.com/api/ideas?token=${this.state.user.userToken}`)
        .then((res) => {
            const ideas = res.data.mData;
            ideas.reverse();
            console.log(res);
            this.setState({ideas});
        });
    }
    newForm = () =>{
        this.state.newForm=true;
        this.setState(this.state); //force re-render
    }
    showIdea = (key) => {
        this.state.showIdea = true;
        this.state.ideaId = key;
        this.setState(this.state); //force re-render
        console.log("Key request:");
        console.log(this.state.ideaId);
    }
    render(){
        if(this.state.newForm){
            this.state.newForm=false;
            return <Redirect to={'/ideas/new'}/> 
        }
        if(this.state.showIdea){
            let path = '/ideas/' + this.state.ideaId;
            this.state.showIdea=false;
            return <Redirect to={path}/> 
        }
        return(
            <Router>
                <div className="spartan">        
                    <div id="viewDescription" className='shadow text-start'>
                        <span className='mx-2'>Ideas</span>
                        <button onClick={this.newForm} type="button" className="btn btn-secondary mx-4 ">New Idea</button>
                    </div>
                    
                    <div className='row'>
                        <span className='p-4 col'>
                            <div id="ideaList">
                                {this.state.ideas.map((ideaData) => <Idea key={ideaData.timestamp} data={ideaData} showIdeaEvent={this.showIdea} />)}
                            </div>
                        </span>
                        
                        <Switch>
                            <Route exact path="/ideas/new" render={() => <IdeaNewForm user={this.state.user}/>}/>
                            <Route path="/ideas/:id" render={() => <IdeaSpecific ideaId={this.state.ideaId} user={this.state.user}/>}/>
                        </Switch>
                        
                        
                    </div>

                       
                    
                </div>                
            </Router>
        ) 
    }
}
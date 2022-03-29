import * as React from 'react';
import { HashRouter as Router, Route, Switch } from 'react-router-dom';
const axios = require('axios');
import {Idea} from './Idea';
import {IdeaNewForm} from './IdeaNewForm';
import {Hello} from '../Hello';

export class IdeaList extends React.Component{
    state = {
        ideas:[]
    }
    componentDidMount(){
        console.log('I was triggered during componentDidMount')
        axios.get("https://cse216-group4-test.herokuapp.com/api/ideas")
        .then((res) => {
            const ideas = res.data.mData;
            ideas.reverse();
            console.log(ideas);
            this.setState({ideas});
        });
    }
    render(){
        return(
            <Router>
                <div className="spartan">
                    <div id="viewDescription" className='position-fixed shadow text-start container-fluid'>Ideas</div>
                    <div id="ideaList" className='p-4'>
                        {this.state.ideas.map((ideaData) => <Idea key={ideaData.timestamp} data={ideaData} />)}
                    </div>
                    <Switch>
                        <Route exact path="/ideas/new" component={IdeaNewForm}/>
                        <Route path="/ideas/:id" render={() => <Hello message={"IDIDID"} />}/>
                    </Switch>   
                    
                </div>                
            </Router>
        ) 
    }
}
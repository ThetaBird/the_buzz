import * as React from 'react';
const axios = require('axios');
import {Idea} from './Idea';

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
            <div id = "ideaList" className="p-4">{
            this.state.ideas.map((ideaData) =>  
                <Idea data={ideaData} />
            )}
            </div>
        ) 
    }
}
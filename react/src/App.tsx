import * as React from "react";
import { HashRouter as Router, Route, Link, Switch, Redirect } from 'react-router-dom';
import { Hello } from "./Hello";
import { IdeaList } from './Components/IdeaList';
import { Login, Logout } from './Components/OAuth';
import { Greeting } from './Components/Greeting';
import { Profile } from './Components/Profile';

export class App extends React.Component{
    /** The global state for this component is a counter */
    state = { 
        user:{
            userToken:""
        },
        viewUser:{},
        refresh:true
    };

    setAppState = (data: any) => {
        this.state.user = data;
        this.setState(this.state);
        console.log(this.state);
    }
    getAppState = () => {
        return this.state.user;
    }
    /** render the component */
    render() {
        const loggedInUser = localStorage.getItem("user");
        if(loggedInUser && this.state.refresh){
            this.state.refresh=false;
            this.setAppState(JSON.parse(loggedInUser))
        }
        return (
            this.state.user.userToken ?
            <Router>
                <div className="row container-fluid">
                    <span id="navCol"className="primary col-2">
                        <div id="buzzHeader"className='spartan h1'>The Buzz</div>
                        <Greeting user={this.state.user}/>
                        <nav id="navHeader" className="row text-center">
                            <Link className="col-3 text-white spartan p-2" to="/">Home</Link>
                            <Link className="col-3 text-white spartan p-2" to="/profile">Profile</Link>
                            <Link className="col-3 text-white spartan p-2" to="/employees">Employees</Link>
                            {/* Also profile indicator, w/ avatar and name w/ logout option */}
                        </nav>
                        <Logout updateData={this.setAppState}/>
                    </span>
                    
                        <span id="contentCol" className="col">
                            <Switch>
                                <Route exact path="/" render={() => <Redirect to={'/ideas'}/>} />

                                <Route exact path="/ideas" render={() => <IdeaList user={this.state.user}/>}/>
                                <Route exact path="/ideas/*" render={() => <IdeaList user={this.state.user}/>}/>

                                <Route exact path="/profile" render={() => <Profile user={this.state.user} viewUser={this.state.user}/>} />    
                                <Route exact path="/profile/*" render={() => <Profile user={this.state.user} viewUser={this.state.viewUser}/>} />

                                <Route exact path="/employees" component={Hello} />
                            </Switch>
                        </span>
                    
                    
                      
                </div>
            </Router>
            :
            <Login updateData={this.setAppState}/>
        );
    }
}
/**
 * LINKS
 * Profile (view avatar, description, liked/disliked ideas, posted ideas, company role)
 * Settings (Light/Dark mode, edit name, avatar)
 * Edit idea (edit subject, attachment, content, or allowed roles)
 * Employee directory (list of profiles and what role in the company they have)
 * 
 * ROUTES
 * ideas (automatic redirect from / if logged in)
 * login (automatic redirect from / if not logged in)
 * profile (accessible from link in header)
 * edit idea (accessible from button in idea)
 * employees (accessible from link in header)
 * 
 */
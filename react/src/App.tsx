import * as React from "react";
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { Hello } from "./Hello";
import { IdeaList } from './Components/IdeaList';


/** App has one property: an ID token from google oath */
type AppProps = { 
    data:{
        token:string
    }
 }

export class App extends React.Component<AppProps> {
    /** The global state for this component is a counter */
    state = { token: null };

    /**
     * When the component mounts, we need to set the initial value of its
     * counter
     */
    componentDidMount = () => { this.setState({ token: this.props.data.token }); }

    /** Get the current value of the counter */
    getToken = () => this.state.token;

    /** Set the counter value */
    setToken = (token: string) => this.setState({ token });

    /** render the component */
    render() {
        return (
            <Router>
                <div>
                    <nav id="navHeader" className="container-fluid p-3 primary text-white text-left">
                        <Link className="text-white spartan p-2" to="/">Home</Link>
                        <Link className="text-white spartan p-2" to="/profile">Profile</Link>
                        <Link className="text-white spartan p-2" to="/employees">Employees</Link>
                        {/* Also profile indicator, w/ avatar and name w/ logout option */}
                    </nav>
                    <Switch>
                        <Route exact path="/" component={Hello} />
                        <Route exact path="/ideas" component={IdeaList} />
                        <Route exact path="/ideas/new" component={Hello} />
                        
                        <Route exact path="/profile" component={Hello} />
                        <Route exact path="/settings" component={Hello} />
                        <Route exact path="/employees" component={Hello} />
                    </Switch>

                    <Route path="/ideas/:id" component={Hello} />   
                </div>
            </Router>
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
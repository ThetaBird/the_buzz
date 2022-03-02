//https://www.bezkoder.com/react-typescript-axios/
//used a lot of this code for setting up react folder
import * as React from "react";
import { useQuery, useMutation } from "react-query";
import "bootstrap/dist/css/bootstrap.min.css";
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { Component, ChangeEvent } from "react";
//import IdeaService from "./services/IdeaService";
import "./style.css";
import IdeaData from "./types/idea.type";
import AddIdea from "./components/Add-Idea.component";
import Idea from "./components/Idea.component";
import IdeasList from "./components/IdeasList.component";
//import IdeaService from "./services/IdeaService.component";
//const backendUrl = "https://cse216-group4-app.herokuapp.com";

const App: React.FC = () => {
    /** render the component */
    //render() {
        return (
            <Router>
                <div>
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <Link to={"/ideas"} className="nav-link">
                            See All Ideas
                        </Link>
                        <Link to="/add" className="nav-link">
                            Post Ideas
                        </Link>
                        <Link to="/ideas" className="nav-link">
                            See Last Idea
                        </Link>
                    </nav>
                    <Switch>
                        <Route exact path={["/", "/ideas"]} component={IdeasList} />
                        <Route exact path="/add" component={AddIdea} />
                        <Route path ="/ideas/:id"component={Idea} />
                    </Switch>
                </div>
            </Router>
        );
    //}
}
export default App;
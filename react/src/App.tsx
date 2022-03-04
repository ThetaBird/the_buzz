//https://www.bezkoder.com/react-typescript-axios/
//used a lot of this code for setting up react folder
var $: any;
import * as React from "react";
import { useQuery, useMutation } from "react-query";
//import "bootstrap/dist/css/bootstrap.min.css";
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { Component, ChangeEvent } from "react";
//import IdeaService from "./services/IdeaService";
//App.use(express.static(__dirname));
import "./style.css";
import "./http-common.ts";
import "./index.html";
//import IdeaData  from "types/idea.type";
import "./ideas.tsx";
import "./index.tsx";
//import IdeaData from "./types/idea.type";
import { AddIdea } from "./components/Add-Idea.component";
import { Idea } from "./components/Idea.component";
import { IdeasList } from "./components/IdeasList.component";
//var ideaList: IdeasList;
//var editIdea: EditIdeaForm;

//import IdeaService from "./services/IdeaService.component";
//const backendUrl = "https://cse216-group4-app.herokuapp.com";


//const App: React.FC = () => {
export class App extends React.Component {
    /*$(document).ready(function () {
        // Create the object that controls the "New Entry" form
        //addIdea = new AddIdea();
        // Create the object for the main data list, and populate it with data from
        // the server
        //ideaList = new IdeasList();
        //editIdea = new EditIdeaForm();
        IdeasList.refresh();
        $("#editElement").hide();
        $("#addElement").hide();
        $("#showElements").show();
        // set up the "Add Message" button
        $("#showFormButton").click(function () {
        $("#addElement").show();
        $("#showElements").hide();
        });
    });*/
    render(){
        return(
            <Router>
            <div>
                <nav className="navbar navbar-expand navbar-dark bg-dark">
                    <Link to="/AddIdea" className="nav-link">
                        AddIdeas
                    </Link>
                    <Link to="/IdeasList" className="nav-link">
                        SeeAllIdeas
                    </Link>
                    <Link to="/Idea" className="nav-link">
                        EditIdeas
                    </Link>
                </nav>
                <Switch>
                    <Route exact path={["/ideas"]} component={AddIdea} />
                    <Route exact path={["/", "/ideas"]} component={IdeasList} />
                    <Route path ={["/ideas/:id"]} component={Idea} />
                </Switch>
            </div>
        </Router>
        );
    }
}
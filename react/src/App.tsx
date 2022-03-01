//https://www.bezkoder.com/react-typescript-axios/
//used a lot of this code for setting up react folder

import * as React from "react";
//import { useQuery, useMutation } from "react-query";
import "bootstrap/dist/css/bootstrap.min.css";
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
//import create from "./components/Create.component";
//import ideaslist from "./components/IdeasList.component";
//import idea from "./components/Idea.component";
//import IdeaData from './types/idea'
import IdeaService from "./services/IdeaService"
import Create from "./components/Create.component"
import Idea from "./components/Idea.component"
import IdeasList from "./components/IdeasList.component"

//const backendUrl = "https://cse216-group4-app.herokuapp.com";

/** App has one property: a number */
//type AppProps = { num: number }

/*const App: React.FC = () => {
    const [getId, setGetId] = useState("");
    const [getTitle, setGetTitle] = useState("");
    const [getResult, setGetResult] = useState<string | null>(null);
    const fortmatResponse = (res: any) => {
      return JSON.stringify(res, null, 2);
    };*/

class App extends React.Component {
    /** The global state for this component is a counter */
    //state = { num: 0 };

    /**
     * When the component mounts, we need to set the initial value of its
     * counter
     */
    //componentDidMount = () => { this.setState({ num: this.props.num }); }

    /** Get the current value of the counter */
    //getNum = () => this.state.num;

    /** Set the counter value */
    //setNum = (num: number) => this.setState({ num });

    /** render the component */
    render() {
        //const App: React.FC = () => {
        return (
            <Router>
                <div>
                    <nav>
                        <Link to="/ideas">See All Ideas</Link>

                        <Link to="/add">Post Ideas</Link>

                        <Link to="/ideas">See Last Idea</Link>

                    </nav>
                    <Switch>
                        <Route exact path={["/", "/ideas"]} component={IdeasList} />
                        <Route exact path="/add" component={Create} />
                        <Route path ="/ideas/:id"component={Idea} />
                    </Switch>
                </div>
            </Router>
        );
        //}
    }
}
export default App;
import { Component, ChangeEvent, ReactNode } from "react";
import { RouteComponentProps } from 'react-router-dom';
//import IdeaService from "../services/IdeaService";
import IdeaData from "../types/idea.type";
import { Link } from "react-router-dom";
import React = require("react");

/*interface RouterProps { // type for `match.params`
  ideaId: string; // must be type `string` since value comes from the URL
}
type Props = RouteComponentProps<RouterProps>;
type State = {
  currentIdea: IdeaData;
  message: string;
}*/
//var idea: Idea
//export default class Idea extends Component<Props, State> {
//export default class Idea extends React.Component<{}, { value: string }>{
//class Idea{
    /*constructor(props: Props) {
        super(props);
        this.getIdea = this.getIdea.bind(this);
        this.state = {
            currentIdea: {
                ideaId: "",
                userId: null,
                timestamp: null,
                subject: "",
                content: "",
                attachment: "",
                allowedRoles: null
            },
            message: "",
        };
    }*/
    /*componentDidMount() {
        this.getIdea(this.props.match.params.ideaId);
    }
    getIdea(ideaId: string) {
        IdeaService.findById(ideaId)
        .then((response: any) => {
            this.setState({
                currentIdea: response.data,
            });
            console.log(response.data);
        })
        .catch((e: Error) => {
            console.log(e);
        });
    }
    /*render(){
        const { currentIdea } = this.state;
        return (
        
        );
    }*/
//}


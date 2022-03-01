import { Component, ChangeEvent, ReactNode } from "react";
import { RouteComponentProps } from 'react-router-dom';
import IdeaService from "../services/IdeaService";
import IdeaData from "../types/idea.type";
import { Link } from "react-router-dom";
import React = require("react");

interface RouterProps { // type for `match.params`
  ideaId: string; // must be type `string` since value comes from the URL
}
type Props = RouteComponentProps<RouterProps>;
type State = {
  currentIdea: IdeaData;
  message: string;
}
export default class Idea extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.getIdea = this.getIdea.bind(this);
        this.state = {
            currentIdea: {
                ideaId: null,
                userId: null,
                timestamp: "",
                subject: "",
                content: "",
                attachment: "",
                allowedRoles: ""
            },
            message: "",
        };
    }
    componentDidMount() {
        this.getIdea(this.props.match.params.ideaId);
    }
    getIdea(id: string) {
        IdeaService.findById(id)
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
    render(){
        const { currentIdea } = this.state;
        return (
            <div className="col-md-6">
            {currentIdea ? (
                <div>
                    <h4>Idea</h4>
                    <div>
                        <label>
                            <strong>Content:</strong>
                        </label>{" "}
                        {currentIdea.content}
                    </div>
                </div>
            ): (
                <div>
                    <br />
                        <p>Please post an idea</p>  
                </div>          
            )}
            </div>
        );
    }
}


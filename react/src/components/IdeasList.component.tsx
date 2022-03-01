import { Component, ChangeEvent } from "react";
import IdeaService from "../services/IdeaService";
import { Link } from "react-router-dom";
import IdeaData from "../types/idea.type";
import React = require("react");

type Props = {};
type State = {
  ideas: Array<IdeaData>,
  currentIdea: IdeaData | null,
  currentIndex: number
};
export default class IdeasList extends Component<Props, State>{
  constructor(props: Props) {
    super(props);
    this.retrieveIdeas = this.retrieveIdeas.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveIdea = this.setActiveIdea.bind(this);
    this.state = {
      ideas: [],
      currentIdea: null,
      currentIndex: -1
    };
  }
  componentDidMount() {
    this.retrieveIdeas();
  }

  retrieveIdeas() {
    IdeaService.findAll()
      .then((response: any) => {
        this.setState({
          ideas: response.data
        });
        console.log(response.data);
      })
      .catch((e: Error) => {
        console.log(e);
      });
  }
  refreshList() {
    this.retrieveIdeas();
    this.setState({
      currentIdea: null,
      currentIndex: -1
    });
  }
  setActiveIdea(idea: IdeaData, index: number) {
    this.setState({
      currentIdea: idea,
      currentIndex: index
    });
  }
  
    render() {
        return (
            <div className="col-md-6">
                {this.retrieveIdeas}
            </div>       
        ); 
    }
}
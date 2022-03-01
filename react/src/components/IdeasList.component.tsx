import { Component, ChangeEvent } from "react";
import IdeaService from "../services/IdeaService";
import { Link } from "react-router-dom";
import IdeaData from "../types/idea.type";
import React = require("react");

type Props = {};
type State = {
  ideas: Array<IdeaData>,
  currentIdea: IdeaData | null,
  currentIndex: number,
  searchId: number | string
};
export default class IdeasList extends Component<Props, State>{
  constructor(props: Props) {
    super(props);
    this.onChangeSearchId = this.onChangeSearchId.bind(this);
    this.retrieveIdeas = this.retrieveIdeas.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveIdea = this.setActiveIdea.bind(this);
    this.searchId = this.searchId.bind(this);
    this.state = {
      ideas: [],
      currentIdea: null,
      currentIndex: -1,
      searchId: 0
    };
  }
  componentDidMount() {
    this.retrieveIdeas();
  }

  onChangeSearchId(e: ChangeEvent<HTMLInputElement>) {
    const s = e.target.value;
    this.setState({
      searchId: s
    });
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
  searchId() {
    this.setState({
      currentIdea: null,
      currentIndex: -1
    });
    IdeaService.findById(this.state.searchId.toString())
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
  
    render() {
        return (
          <div className="list row">
          <div className="col-md-8">
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Search by id"
              value={this.state.searchId}
              onChange={this.onChangeSearchId}
            />
            <div className="input-group-append">
              <button
                className="btn btn-outline-secondary"
                type="button"
                onClick={this.searchId}
              >
                Search
              </button>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <h4>Ideas List</h4>
          <ul className="list-group">
            {this.state.ideas &&
              this.state.ideas.map((idea: IdeaData, index: number) => (
                <li
                  className={
                    "list-group-item " +
                    (index == this.state.currentIndex ? "active" : "")
                  }
                  onClick={() => this.setActiveIdea(idea, index)}
                  key={index}
                >
                  {idea.ideaId}
                </li>
              ))}
          </ul>
        </div>
        <div className="col-md-6">
          {this.state.currentIdea ? (
            <div>
              <h4>Idea</h4>
              <div>
                <label>
                  <strong>Subject:</strong>
                </label>{" "}
                {this.state.currentIdea.subject}
              </div>
              <div>
                <label>
                  <strong>Content:</strong>
                </label>{" "}
                {this.state.currentIdea.content}
              </div>
              <div>
                <label>
                  <strong>Attachment:</strong>
                </label>{" "}
                {this.state.currentIdea.attachment}
              </div>
              <Link
                to={"/ideas/" + this.state.currentIdea.ideaId}
                className="badge badge-warning"
              >
                Edit
              </Link>
            </div>
          ) : (
            <div>
              <br />
              <p>Please click on an Idea...</p>
            </div>
          )}
        </div>
      </div>    
        ); 
    }
}
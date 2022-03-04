import { Component, ChangeEvent } from "react";
import IdeaService from "../services/IdeaService";
import { Link } from "react-router-dom";
import IdeaData from "../types/idea.type";
import React = require("react");
var $: any;
type Props = {};
type State = {
  ideas: Array<IdeaData>,
  currentIdea: IdeaData | null,
  currentIndex: number,
  searchId: number | string
};

export class IdeasList extends Component<Props, State>{
  static refresh() {
      throw new Error("Method not implemented.");
  }
  static refreshList: any;
  constructor(props: Props) {
    super(props);
    $("#delbtn").click(this.clickDelete);
    $("#editbtn").click(this.clickEdit);
    this.onChangeSearchId = this.onChangeSearchId.bind(this);
    this.retrieveIdeas = this.retrieveIdeas.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveIdea = this.setActiveIdea.bind(this);
    this.searchId = this.searchId.bind(this);
    this.update = this.update.bind(this);
    this.buttons = this.buttons.bind(this);
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
    IdeaService.getAll()
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
  /*refresh() {
      return $.ajax({
          method: "GET",
          url: "https://cse216-group4-app.herokuapp.com/api/ideas",
          dataType: "application/json",
          data: JSON.stringify({ ideaId: data.ideaId, userId: data.userId, timestamp: data.timestamp, subject: data.subject, content: data.content, attachment: data.attachment, allowedRoles: data.allowedRoles }),
          success: ideaList.update
      });
    }*/
  
    /**
     * update is the private method used by refresh() to update messageList
     */
  update(data: any) {
      $("#IdeasList").html("<table>");
      for (let i = 0; i < data.mData.length; ++i) {
          $("#IdeasList > tbody").append("<tr><td>" + data.mData[i].ideaId + "</td>" + data.mData[i].userId + data.mData[i].timestamp + data.mData[i].title + data.mData[i].subject + data.mData[i].content + data.mData[i].attachment + data.mData[i].allowedRoles +
          "</td>" + this.buttons(data.mData[i].ideaId) + "</tr>");
      }
      $("#IdeasList").append("</table>");
      // Find all of the delete buttons, and set their behavior
      $(".delbtn").click(this.clickDelete);
      $(".editbtn").click(this.clickEdit);
  }
  
    /**
     * buttons() doesn't do anything yet
     */
  buttons(id: string): string {
    return "<td><button class='editbtn' data-value='" + id
    + "'>Edit</button></td>"
    + "<td><button class='delbtn' data-value='" + id
    + "'>Delete</button></td>";
  }
  /**
  * clickDelete is the code we run in response to a click of a delete button
  */
  clickDelete() {
    // for now, just print the ID that goes along with the data in the row
    // whose "delete" button was clicked
    let id = $(this).data("value");
    return $.ajax({
        method: "DELETE",
        url: "https://cse216-group4-app.herokuapp.com/api/ideas/:" + id,
        dataType: "application/json",
          // TODO: we should really have a function that looks at the return
          //       value and possibly prints an error message.
        success: this.refreshList
    });
  }
  /**
  * clickEdit is the code we run in response to a click of a delete button
  */
  private clickEdit() {
      // as in clickDelete, we need the ID of the row
      let id = $(this).data("value");
      return $.ajax({
          method: "GET",
          url: "https://cse216-group4-app.herokuapp.com/api/idea/:" + id,
          dataType: "application/json",
          success: this.update
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
          <h4>All Ideas</h4>
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
                  <strong>UserId:</strong>
                </label>{" "}
                {this.state.currentIdea.userId}
              </div>
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
              <div>
                <label>
                  <strong>AllowedRoles:</strong>
                </label>{" "}
                {this.state.currentIdea.allowedRoles}
              </div>
              <Link
                to={"/ideas/" + this.state.currentIdea.userId}
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
  /*refresh() {
    // Issue a GET, and then pass the result to update()
    $.ajax({
      type: "GET",
      url: "/ideas",
      dataType: "json",
      success: ideaList.update
    });
  }

  /**
   * update is the private method used by refresh() to update messageList
   */
  /*private update(data: any) {
      $("#ideaList").html("<table>");
      for (let i = 0; i < data.mData.length; ++i) {
          $("#ideaList").append("<tr><td>" + data.mData[i].title + data.mData[i].subject + data.mData[i].content + data.mData[i].attachment + data.mData[i].allowedRoles +
              "</td>" + ideaList.buttons(data.mData[i].ideaId) + "</tr>");
      }
      $("#ideaList").append("</table>");
  }*/

  /**
   * buttons() doesn't do anything yet
   */
  /*private buttons(id: string): string {
      return "";
  }
}*/
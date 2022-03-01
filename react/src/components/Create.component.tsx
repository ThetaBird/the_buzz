import { Component, ChangeEvent } from "react";
import React = require("react");
import IdeaService from "../services/IdeaService";
import IdeaData from '../types/idea.type';
type Props = {};
type State = IdeaData & {
  submitted: boolean
};
export default class Create extends Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.onChangeIdeaId = this.onChangeIdeaId.bind(this);
        this.onChangeSubject = this.onChangeSubject.bind(this);
        this.onChangeContent = this.onChangeContent.bind(this);
        this.onChangeAttachment = this.onChangeAttachment.bind(this);
        this.onChangeAllowedRoles = this.onChangeAllowedRoles.bind(this);
        this.saveIdea = this.saveIdea.bind(this);
        this.newIdea = this.newIdea.bind(this);
        this.state = {
            ideaId: "",
            userId: null,
            timestamp: null,
            subject: "",
            content: "",
            attachment: "",
            allowedRoles: null,
            submitted: false
        };
    }
    onChangeIdeaId(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({ideaId: e.target.value});
    }
    onChangeSubject(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({subject: e.target.value});
    }
    onChangeContent(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({content: e.target.value});
    }
    onChangeAttachment(e: React.ChangeEvent<HTMLInputElement>){
        this.setState({attachment: e.target.value});
    }
    onChangeAllowedRoles(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({allowedRoles: e.target.value});
    }
    saveIdea() {
        const data: IdeaData = {
            //should maybe generate a random number for this
            ideaId: this.state.ideaId,
            //should get the id of the user
            userId: "001",
            timestamp: 0o10,
            //set to a real time
            subject: this.state.subject,
            content: this.state.content,
            attachment: this.state.attachment,
            allowedRoles: this.state.allowedRoles
    };
    IdeaService.create(data)
      .then((response: any) => {
        this.setState({
            ideaId: response.data.ideaId,
            subject: response.data.subject,
            content: response.data.content,
            attachment: response.data.attachment,
            allowedRoles: response.data.allowedRoles,
            submitted: true
        });
        console.log(response.data);
      })
      .catch((e: Error) => {
        console.log(e);
      });
    }
    newIdea() {
        this.setState({
            ideaId: "",
            userId: null,
            timestamp: null,
            subject: "",
            content: "",
            attachment: "",
            allowedRoles: null,
            submitted: false
        });
    }
    render() {
        const { submitted, ideaId, subject, content, attachment, allowedRoles } = this.state;
        return (
            <div className="submit-form">
            {submitted ? (
                <div>
                    <h4>You submitted successfully!</h4>
                    <button className="btn btn-success" onClick={this.newIdea}>
                        Add
                    </button>
                </div>
            ) : (
            <div>
                <div className="form-group">
                <label htmlFor="subject">subject</label>
                <input
                    type="text"
                    className="form-control"
                    id="subject"
                    required
                    value={subject}
                    onChange={this.onChangeSubject}
                    name="subject"
                />
            </div>
                <div className="form-group">
                <label htmlFor="content">content</label>
                <input
                    type="text"
                    className="form-control"
                    id="content"
                    required
                    value={content}
                    onChange={this.onChangeContent}
                    name="content"
                />
            </div>
                <div className="form-group">
                <label htmlFor="attachment">attachment</label>
                <input
                    type="text"
                    className="form-control"
                    id="attachment"
                    required
                    value={attachment}
                    onChange={this.onChangeAttachment}
                    name="attachment"
                />
            </div>
            <button onClick={this.saveIdea} className="btn btn-success">
                Submit
            </button>
        </div>
    )}
    </div>
    );
  }
}
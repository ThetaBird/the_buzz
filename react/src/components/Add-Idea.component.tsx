var $: any;
import { Component, ChangeEvent } from "react";
import React = require("react");
import IdeaService from "../services/IdeaService";
import IdeaData from '../types/idea.type';
//import { randomInt } from "node:crypto";
import { render } from "react-dom";
//var AddIdea: addIdea;
type Props = {};
type State = IdeaData & {
  submitted: boolean
};
//deleted default class
export class AddIdea extends Component<Props, State> {
/**
 * NewEntryForm encapsulates all of the code for the form for adding an entry
 */
//class addIdea {
    /**
     * To initialize the object, we say what method of NewEntryForm should be
     * run in response to each of the form's buttons being clicked.
     */
     constructor(props: Props) {
        super(props);
        $("#addCancel").click(this.clearForm);
        $("#addButton").click(this.submitForm);
        this.onSubmitResponse = this.onSubmitResponse.bind(this);
        this.onChangeUserId = this.onChangeUserId.bind(this);
        this.onChangeSubject = this.onChangeSubject.bind(this);
        this.onChangeContent = this.onChangeContent.bind(this);
        this.onChangeAttachment = this.onChangeAttachment.bind(this);
        this.onChangeAllowedRoles = this.onChangeAllowedRoles.bind(this);
        //this.saveTutorial = this.saveTutorial.bind(this);
        //this.newTutorial = this.newTutorial.bind(this);
        this.state = {
            ideaId: "",
            userId: "",
            timestamp: null,
            subject: "",
            content: "",
            attachment: "",
            allowedRoles: "",
            submitted: false
        };
    }

    /**
     * Clear the form's input fields
     */
    clearForm() {
        $("newUserId").val("");
        $("#newSubject").val("");
        $("#newContent").val("");
        $("#newAttachment").val("");
        $("#newAllowedRoles").val("");
    }

    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     */
    submitForm() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        let id = "" + $("#newUserId").val("");
        let sub = "" + $("#newSubject").val("");
        let cont = "" + $("#newContent").val("");
        let att = "" + $("#newAttachment").val("");
        let ar = "" + $("#newAllowedRoles").val("");
        if(id === ""){
            window.alert("Error: User ID is not valid");
            return;
        }
        if (sub === "") {
            window.alert("Error: subject is not valid");
            return;
        }
        if (cont === "") {
            window.alert("Error: Content is not valid");
            return;
        }
        if (att === "") {
            window.alert("Error: Attachment is not valid");
            return;
        }
        if (ar === "") {
            window.alert("Error: allowedRoles is not valid");
            return;
        }
        // set up an AJAX post.  When the server replies, the result will go to
        // onSubmitResponse
        /*return $.ajax({
            method: "POST",
            url: "https://cse216-group4-test.herokuapp.com/api/ideas",
            //url: "http://localhost:8080/api/ideas",
            dataType: "application/json",
            //data: JSON.stringify({ userId: id, subject: sub, content: cont, attachment: att, allowedRoles: ar }),
            data: "../public/ideas.json",
            success: AddIdea.onSubmitResponse
        });*/
        const data: IdeaData = {
            //should maybe generate a random number for this
            ideaId:  "001",
            //should get the id of the user
            userId: id,
            timestamp: 0o10,
            //set to a real time
            subject: sub,
            content: cont,
            attachment: att,
            allowedRoles: ar
        };
        IdeaService.create(data)
        .then((response: any) => {
            this.setState({
                userId: response.data.ideaId,
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

    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a 
     * result.
     * 
     * @param data The object returned by the server
     */
    onSubmitResponse(e: React.ChangeEvent<HTMLInputElement>) {
        this.clearForm();
    }
 // end class NewEntryForm
    
    onChangeUserId(e: React.ChangeEvent<HTMLInputElement>) {
        this.setState({userId: e.target.value});
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
    /*saveIdea() {
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
    } */
    /*newIdea() {
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
        //this.clearForm();
    }*/
    render() {
        const { submitted, userId, subject, content, attachment, allowedRoles } = this.state;
        return (
            <div className="submit-form">
            {submitted ? (
                <div>
                    <h4>You submitted successfully!</h4>
                    <button className="btn btn-success" onClick={this.clearForm}>
                        Add
                    </button>
                </div>
            ) : (
            <div>
            <div className="form-group">
                <label htmlFor="userId">subject</label>
                <input
                    type="text"
                    className="form-control"
                    id="userId"
                    required
                    value={userId}
                    onChange={this.onChangeUserId}
                    name="userId"
                />
            </div>
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
            <div className="form-group">
                <label htmlFor="allowedRoles">Allowed Roles</label>
                <input
                    type="text"
                    className="form-control"
                    id="allowedRoles"
                    required
                    value={allowedRoles}
                    onChange={this.onChangeAllowedRoles}
                    name="allowedRoles"
                />
            </div>
            <button onClick={this.submitForm} className="btn btn-success">
                Submit
            </button>
        </div>
    )}
    </div>
    );
    }
}
//export default AddIdea;
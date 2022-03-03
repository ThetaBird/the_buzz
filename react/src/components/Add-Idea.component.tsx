var $: any;
import { Component, ChangeEvent } from "react";
import React = require("react");
//import IdeaService from "../services/IdeaService";
import IdeaData from '../types/idea.type';
//import { randomInt } from "node:crypto";
import { render } from "react-dom";
//var $: any;
var AddIdea: addIdea;
/*type Props = {};
type State = IdeaData & {
  submitted: boolean
};*/
//export default class AddIdea extends Component<Props, State> {
class addIdea{
    //[x: string]: any;
    constructor() {
        //super(props);
        $("#addCancel").click(this.clearForm);
        $("#addButton").click(this.submitForm);
        //this.onSubmitResponse = this.onSubmitResponse.bind(this);
        //this.onChangeIdeaId = this.onChangeIdeaId.bind(this);
        //this.onChangeSubject = this.onChangeSubject.bind(this);
        //this.onChangeContent = this.onChangeContent.bind(this);
        //this.onChangeAttachment = this.onChangeAttachment.bind(this);
        //this.onChangeAllowedRoles = this.onChangeAllowedRoles.bind(this);
        //this.saveIdea = this.saveIdea.bind(this);
        //this.newIdea = this.newIdea.bind(this);
        /*this.state = {
            ideaId: "",
            userId: null,
            timestamp: null,
            subject: "",
            content: "",
            attachment: "",
            allowedRoles: null,
            submitted: false
        };*/
    }
    /**
     * Clear the form's input fields
     */
     clearForm() {
        //$("ideaId").val("");
        $("#newSubject").val("");
        $("#newContent").val("");
        $("#newAttachment").val("");
        $("#allowedRoles").val("");
    }

    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     */
     submitForm() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        //can you generate a random number? can't use crypto package won't run
        let ideaId = '001';
        let subject = "" + $("#newSubject").val();
        if (subject === "") {
            window.alert("Error: subject is not valid");
            return;
        }
	    let content = "" + $("#newContent").val();
            if (content === "") {
                window.alert("Error: Content is not valid");
                return;
            }
        let attachment = "" + $("#newAttachment").val();
        if (attachment === "") {
            window.alert("Error: Attachment is not valid");
            return;
        }
        let allowedRoles = "" + $("#allowedRoles").val();
        if (allowedRoles === "") {
            window.alert("Error: allowedRoles is not valid");
            return;
        }
        /*const data: IdeaData = {
            ideaId: this.state.ideaId,
            //should get the id of the user
            userId: "001",
            timestamp: 0o10,
            //set to a real time
            subject: this.state.subject,
            content: this.state.content,
            attachment: this.state.attachment,
            allowedRoles: this.state.allowedRoles
        }
        IdeaService.create(data)
        .then((response: any) => {
            this.setState({
                ideaId: response.data.ideaId,
                userId: response.data.userId,
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
        });*/
        $.ajax({
            type: "POST",
            url: "/ideas",
            dataType: "json",
            data: JSON.stringify({ subject: subject, content: content, attachment: attachment, allowedRoles: allowedRoles }),
            success: AddIdea.onSubmitResponse
        });
    }
    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a
     * result.
     *
     * @param data The object returned by the server
     */
    private onSubmitResponse(data: any) {
        // If we get an "ok" message, clear the form
        if (data.mStatus=== "ok") {
            this.clearForm();
        }
        // Handle explicit errors with a detailed popup message
        else if (data.mStatus === "error") {
            window.alert("The server replied with an error:\n" + data.mMessage);
        }
        // Handle other errors with a less-detailed popup message
        else {
            window.alert("Unspecified error");
        }
    }
    
    /*onChangeIdeaId(e: React.ChangeEvent<HTMLInputElement>) {
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
    }*/
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
    }
    render() {
        const { submitted, ideaId, subject, content, attachment, allowedRoles } = this.state;
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
  }*/
}
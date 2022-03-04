var $: any;
import { Component, ChangeEvent, ReactNode } from "react";
import { RouteComponentProps } from 'react-router-dom';
import IdeaService from "../services/IdeaService";
import IdeaData from "../types/idea.type";
import { IdeasList } from "./IdeasList.component";
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
//var idea: Idea
export class Idea extends Component<Props, State> {
//export default class Idea extends React.Component<{}, { value: string }>{
//class Idea{
    constructor(props: Props) {
        super(props);
        $("#editCancel").click(this.clearForm);
        $("#editButton").click(this.submitForm);
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
    }
    componentDidMount() {
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

    /**
     * init() is called from an AJAX GET, and should populate the form if and 
     * only if the GET did not have an error
     */
    init(data: any) {
        if (data.mStatus === "ok") {
            $("#editUserId").val(data.mData.userId);
            $("#editSubject").val(data.mData.subject);
            $("#editContent").val(data.mData.content);
            $("#editAttachment").val(data.mData.attachment);
            $("#editAllowedRoles").text(data.mData.allowedRoles);
            // show the edit form
            $("#addElement").hide();
            $("#editElement").show();
            $("#showElements").hide();
        }
        else if (data.mStatus === "error") {
            window.alert("Error: " + data.mMessage);
        }
        else {
            window.alert("An unspecified error occurred");
        }
    }

    /**
     * Clear the form's input fields
     */
    clearForm() {
        $("#userId").val("");
        $("#editSubject").val("");
        $("#editContent").val("");
        $("#editAttachment").val("");
        $("#editAllowedRoles").text("");
        // reset the UI
        $("#addElement").hide();
        $("#editElement").hide();
        $("#showElements").show();
    }

    /**
     * Check if the input fields are both valid, and if so, do an AJAX call.
     */
    submitForm() {
        // get the values of the two fields, force them to be strings, and check 
        // that neither is empty
        let id = "" + $("#editUserId").val();
        let sub = "" + $("#editSubject").val();
        let cont = "" + $("#editContent").val();
        let att = "" + $("#editAttachment").val();
        let ar = "" + $("#editAllowedRoles").val();
        if (sub === "" || cont === "") {
            window.alert("Error: subject or content is not valid");
            return;
        }
        // set up an AJAX post.  When the server replies, the result will go to
        // onSubmitResponse
        return $.ajax({
            method: "PUT",
            url: "https://cse216-group4-app.herokuapp.com/api/idea/:" + id,
            dataType: "application/json",
            data: JSON.stringify({ userId: id, subject: sub, content: cont, attachment: att, allowedRoles: ar }),
            success: this.onSubmitResponse
        });
    }

    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a 
     * result.
     * 
     * @param data The object returned by the server
     */
    onSubmitResponse(data: any) {
        // If we get an "ok" message, clear the form and refresh the main 
        // listing of messages
        if (data.mStatus === "ok") {
            this.clearForm();
            IdeasList.refreshList;
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
}
export default Idea;
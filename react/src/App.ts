//https://www.bezkoder.com/react-typescript-axios/
//used a lot of this code for setting up react folder
var $: any;
import * as React from "react";
import { useQuery, useMutation } from "react-query";
//import "bootstrap/dist/css/bootstrap.min.css";
import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { Component, ChangeEvent } from "react";
//import IdeaService from "./services/IdeaService";
app.use(express.static(__dirname));
import "../public/style.css";
import "http-common.ts";
import "index.html";
import IdeaData from "types/idea.type";
import "ideas.tsx";
import "index.tsx";
//import IdeaData from "./types/idea.type";
//import AddIdea from "./components/Add-Idea.component";
//import Idea from "./components/Idea.component";
//import IdeasList from "./components/IdeasList.component";
//import IdeaService from "./services/IdeaService.component";
//const backendUrl = "https://cse216-group4-app.herokuapp.com";

/*const App: React.FC = () => {
    //render() {
        return (
            <Router>
                <div>
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <Link to={"/ideas"} className="nav-link">
                            See All Ideas
                        </Link>
                        <Link to="/add" className="nav-link">
                            Post Ideas
                        </Link>
                        <Link to="/ideas" className="nav-link">
                            See Last Idea
                        </Link>
                    </nav>
                    <Switch>
                        <Route exact path={["/", "/ideas"]} component={IdeasList} />
                        <Route exact path="/add" component={AddIdea} />
                        <Route path ="/ideas/:id"component={Idea} />
                    </Switch>
                </div>
            </Router>
        );
    //}
}
export default App; */
var ideaList: IdeasList;
var editIdea: EditIdeaForm;
var AddIdea: addIdea;

/**
 * NewEntryForm encapsulates all of the code for the form for adding an entry
 */
 class addIdea {
    /**
     * To initialize the object, we say what method of NewEntryForm should be
     * run in response to each of the form's buttons being clicked.
     */
    constructor() {
        $("#addCancel").click(this.clearForm);
        $("#addButton").click(this.submitForm);
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
        return $.ajax({
            method: "POST",
            url: "https://cse216-group4-test.herokuapp.com/api/ideas",
            //url: "http://localhost:8080/api/ideas",
            dataType: "application/json",
            //data: JSON.stringify({ userId: id, subject: sub, content: cont, attachment: att, allowedRoles: ar }),
            data: "../public/ideas.json",
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
} // end class NewEntryForm


class IdeasList{
    refresh() {
        return $.ajax({
            method: "GET",
            url: "https://cse216-group4-app.herokuapp.com/api/ideas",
            dataType: "application/json",
            data: JSON.stringify({ ideaId: data.ideaId, userId: data.userId, timestamp: data.timestamp, subject: data.subject, content: data.content, attachment: data.attachment, allowedRoles: data.allowedRoles }),
            success: ideaList.update
        });
      }
    
      /**
       * update is the private method used by refresh() to update messageList
       */
    private update(data: any) {
        $("#ideaList").html("<table>");
        for (let i = 0; i < data.mData.length; ++i) {
            $("#ideaList > tbody").append("<tr><td>" + data.mData[i].ideaId + "</td>" + data.mData[i].userId + data.mData[i].timestamp + data.mData[i].title + data.mData[i].subject + data.mData[i].content + data.mData[i].attachment + data.mData[i].allowedRoles +
            "</td>" + ideaList.buttons(data.mData[i].ideaId) + "</tr>");
        }
        $("#ideaList").append("</table>");
        // Find all of the delete buttons, and set their behavior
        $(".delbtn").click(ideaList.clickDelete);
        $(".editbtn").click(ideaList.clickEdit);
    }
    
      /**
       * buttons() doesn't do anything yet
       */
    private buttons(id: string): string {
        return "<td><button class='editbtn' data-value='" + id
        + "'>Edit</button></td>"
        + "<td><button class='delbtn' data-value='" + id
        + "'>Delete</button></td>";
    }
    /**
    * clickDelete is the code we run in response to a click of a delete button
    */
    private clickDelete() {
        // for now, just print the ID that goes along with the data in the row
        // whose "delete" button was clicked
        let id = $(this).data("value");
        return $.ajax({
            method: "DELETE",
            url: "https://cse216-group4-app.herokuapp.com/api/ideas/:" + id,
            dataType: "application/json",
            // TODO: we should really have a function that looks at the return
            //       value and possibly prints an error message.
            success: ideaList.refresh
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
            success: editIdea.init
        });
    }
}
/**
 * EditEntryForm encapsulates all of the code for the form for editing an entry
 */
 class EditIdeaForm {
    /**
     * To initialize the object, we say what method of EditEntryForm should be
     * run in response to each of the form's buttons being clicked.
     */
    constructor() {
        $("#editCancel").click(this.clearForm);
        $("#editButton").click(this.submitForm);
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
            success: editIdea.onSubmitResponse
        });
    }

    /**
     * onSubmitResponse runs when the AJAX call in submitForm() returns a 
     * result.
     * 
     * @param data The object returned by the server
     */
    private onSubmitResponse(data: any) {
        // If we get an "ok" message, clear the form and refresh the main 
        // listing of messages
        if (data.mStatus === "ok") {
            editIdea.clearForm();
            ideaList.refresh();
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

$(document).ready(function () {
    // Create the object that controls the "New Entry" form
    AddIdea = new addIdea();
    // Create the object for the main data list, and populate it with data from
    // the server
    ideaList = new IdeasList();
    editIdea = new EditIdeaForm();
    ideaList.refresh();
    $("#editElement").hide();
    $("#addElement").hide();
    $("#showElements").show();
    // set up the "Add Message" button
    $("#showFormButton").click(function () {
    $("#addElement").show();
    $("#showElements").hide();
});
});
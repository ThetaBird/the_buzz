import * as React from 'react';
import {CommentReactions} from './CommentReactions'
type CommentProps = {
    data:any  
}

export class Comment extends React.Component<CommentProps>{
    static defaultProps = { 
        data:{
            ideaId: -1,
            userId: -1,
            timestamp: -1,
            content: "null"
        },
     };
     state = {
         parsedDate:"",
     };
     parseDate(){
        let date = new Date(this.props.data.timestamp);
        this.props.data.key = this.props.data.timestamp;
        this.state.parsedDate = 
        (date.getMonth()+1)+
        "/"+date.getDate()+
        "/"+date.getFullYear()+
        " "+date.getHours()+
        ":"+date.getMinutes()+
        ":"+date.getSeconds();
        //console.log(this.props.data.timestamp);
     }
     render() {
         this.parseDate();
        return(
            <div id={this.props.data.key}className="shadow-sm commentContainer">
                <div className="form-row h6 text-start commentUser">
                    <div className="col-sm-1">{this.props.data.userId}</div>
                    <div className="col-sm-3">{this.state.parsedDate}</div>
                    <div className="col-sm-2 text-end"><CommentReactions ideaData={this.props.data}/></div>
                </div>
                <div className="form-row">
                    <div className='col-md-8 commentContent'>{this.props.data.content}</div>
                </div>
            </div>
        );}
}
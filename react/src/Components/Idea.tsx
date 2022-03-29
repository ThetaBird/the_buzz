import * as React from 'react';

type IdeaProps = {
    data:{
        ideaId: number,
        userId: number,
        timestamp:any,

        subject: string,
        content: string,
        attachment: string,
        allowedRoles: number[],
        key: string
    },
    showIdeaEvent: any
    
}

export class Idea extends React.Component<IdeaProps>{
    static defaultProps = { 
        data:{
            ideaId: -1,
            userId: -1,
            timestamp: -1,
            subject: "null",
            content: "null",
            attachment: "null",
            allowedRoles: []
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
        let showIdeaEvent = this.props.showIdeaEvent
        return(
            <div id={this.props.data.key} onClick={() => showIdeaEvent(this.props.data.key)} className="shadow-sm ideaContainer">
                <div className="row h6 text-start ideaUser">
                    <div className="col-sm-1">@User{this.props.data.userId}</div>
                    <div className="col-sm-3">{this.state.parsedDate}</div>
                </div>
                <div className="row">
                    <div className="col-sm-8 h3 ideaSubject">{this.props.data.subject}</div>
                    <div className="col-sm-2 text-end">Likes/Dislikes</div>
                </div>
                <div className="row">
                    <div className='col-md-8 ideaContent'>{this.props.data.content}</div>
                </div>
            </div>
        );}
}
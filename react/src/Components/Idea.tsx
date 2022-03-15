import * as React from 'react'

type IdeaProps = {
    data:{
        ideaId: number,
        userId: number,
        timestamp:any,

        subject: string,
        content: string,
        attachment: string,
        allowedRoles: number[]
    }
    
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
        }
     };
     parseDate(){
        let date = new Date(this.props.data.timestamp);
        this.props.data.timestamp = 
        (date.getMonth()+1)+
        "/"+date.getDate()+
        "/"+date.getFullYear()+
        " "+date.getHours()+
        ":"+date.getMinutes()+
        ":"+date.getSeconds();
        //console.log(this.props.data.timestamp);
     }
     render() {
         //console.log(this.props);
         this.parseDate();
         return(
        <div className='spartan ideaBox'>
            <div className="row h6 text-start ideaUser">
                <div className="col-sm-1">@User{this.props.data.userId}</div>
                <div className="col-sm-3">{this.props.data.timestamp}</div>
                </div>
            <div className="row">
                <div className="col-sm-4 h3 ideaSubject">{this.props.data.subject}</div>
                <div className="col-sm-2 text-end">Likes/Dislikes</div>
            </div>
            <div className="row">
                <div className='col-md-8 ideaContent'>{this.props.data.content}</div>
            </div>
        </div>
     );}
}
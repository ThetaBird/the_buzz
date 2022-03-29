import * as React from 'react';

type CommentProps = {
    data:{
        ideaId: number,
        userId: number,
        timestamp:any,
        content: string,
        key: string
    }
    
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
     render() {
        return(
            <div id={this.props.data.key}className="shadow-sm commentContainer">
                <div className="form-row h6 text-start commentUser">
                    <div className="col-sm-1">@User{this.props.data.userId}</div>
                    <div className="col-sm-3">{this.state.parsedDate}</div>
                    <div className="col-sm-2 text-end">Likes/Dislikes</div>
                </div>
                <div className="form-row">
                    <div className='col-md-8 commentContent'>{this.props.data.content}</div>
                </div>
            </div>
        );}
}
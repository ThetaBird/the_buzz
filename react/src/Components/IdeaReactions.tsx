import * as React from 'react';
type LikeProps = {
    ideaData:any
}
export class IdeaReactions extends React.Component<LikeProps>{
    state = {
        netLikes:0,
        reactionAsset:"",
        numComments:0
    }
    calculateReactions(){
        console.log(this.props.ideaData);
        let netLikes = this.props.ideaData.numLikes - this.props.ideaData.numDislikes;
        this.state.netLikes = netLikes;
        this.state.numComments = this.props.ideaData.comments.length
        if(netLikes == 0){ //neutral, show -
            this.state.reactionAsset = "neutral";            
        }else if(netLikes > 0){ //positive, show ^
            this.state.reactionAsset = "positive";
        }else{ //negative, show v
            this.state.reactionAsset = "negative";
        }
        
    }
    render(){
        this.calculateReactions();
        return(
            <span className='reactionContainer'>
               <span>{this.state.reactionAsset}</span>
               <span>({this.state.netLikes} Likes)</span>
               <span>{this.state.numComments} Comments</span>
            </span> 
            
        )
    }
}
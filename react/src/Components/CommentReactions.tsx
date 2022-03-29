import * as React from 'react';
type LikeProps = {
    ideaData:any
}
export class CommentReactions extends React.Component<LikeProps>{
    state = {
        netLikes:0,
        reactionAsset:"",
    }
    calculateReactions(){
        console.log(this.props.ideaData);
        let netLikes = this.props.ideaData.numLikes - this.props.ideaData.numDislikes;
        this.state.netLikes = netLikes;
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
            <span>
               <span>{this.state.reactionAsset}</span>
               <span>{this.state.netLikes}</span>
            </span> 
            
        )
    }
}
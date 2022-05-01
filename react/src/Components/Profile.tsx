import * as React from 'react';

type ProfileProps = {
    viewUser:any,
    user:any
}
export class Profile extends React.Component<ProfileProps>{
    render(){
        return(
            <div className="spartan">        
                    <div id="viewDescription" className='shadow text-start'>
                        <span className='mx-2'>Profile</span>
                    </div>
                    <div id="profileContainer" className='row'>
                        <img className="col-3" id="profileImage" src={this.props.viewUser.userAvatar}/>
                        <span id="profileInfoHolder" className='col'>
                            <div>{this.props.viewUser.userName}</div>
                            <div id="profileEmail" >{this.props.viewUser.userEmail}</div>
                        </span>
                    </div> 
            </div>
        );
    }
}
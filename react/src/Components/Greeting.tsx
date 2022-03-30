import * as React from 'react';

type GreetingProps = {
    user:any
}
export class Greeting extends React.Component<GreetingProps>{
    render(){
        return (
            <div id="greetingContainer" className='text-white'>
                <div className='h2'>Welcome,</div>
                <div className='h1'>{this.props.user.userName.split(" ")[0]}</div>
            </div>
        )
    }
}
import * as React from 'react'

/**
 * We want everything to be strongly typed, so let's start by declaring a type
 * for the properties that our Hello component will use.
 */
//type IdeaProps = {
    /** The only property we will have is a message */
//    message: string;
//}

/**
 * Declare our component as a class, so that TypeScript can make sure the
 * properties are type-checked.
 */
//export class Ideas extends React.Component<IdeaProps> {
    /** This is how we declare default values for the properties */
//    static defaultProps = { message: "World" };

    /**
     * The render function will return a JSX element.  In the jsx, we use the
     * "{}" syntax to read fields of the Hello class, using "one-way" binding.
     */
//    render() { return <h1>Hello, {this.props.message}</h1>; }
//}

import ReactDOM from 'react-dom';
import App from './App';

import { QueryClient, QueryClientProvider } from "react-query";
const queryClient = new QueryClient();
ReactDOM.render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
import * as React from 'react'
import * as ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";

import App from "./App"
import "./style.css"

/*ReactDOM.render(
    <BrowserRouter>
    <App num={21} />
    </BrowserRouter>, 
    document.getElementById('app')
    );*/

//import App from './App';
//import { QueryClient, QueryClientProvider } from "react-query";
//const queryClient = new QueryClient();
/*ReactDOM.render(
    <browserRouter>
    <React.StrictMode>
    <QueryClientProvider client={queryClient}>
        <App />
    </QueryClientProvider>
    </React.StrictMode>
    </browserRouter>
    document.getElementById('root')
);*/
ReactDOM.render(
    <BrowserRouter>
      <App />
    </BrowserRouter>,
    //document.getElementById('root')
    document.body.appendChild(document.createElement("div"))
  );
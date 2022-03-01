import * as React from 'react'
import * as ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
//import cors from 'cors';

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

// Add a list of allowed origins.
// If you have more origins you would like to add, you can add them to the array below.
//const allowedOrigins = ['http://localhost:8080'];

//const options: cors.CorsOptions = {
//  origin: allowedOrigins
//};

ReactDOM.render(
    <BrowserRouter>
      <App />
    </BrowserRouter>,
    //document.getElementById('root')
    document.body.appendChild(document.createElement("div"))
  );
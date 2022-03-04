import * as React from 'react'
import * as ReactDOM from "react-dom";
//import { BrowserRouter } from "react-router-dom";
//import cors from 'cors';
//import { HashRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { HashRouter } from 'react-router-dom';

import { App } from "./App"
import "./style.css"


ReactDOM.render(
    <HashRouter>
      <App />
    </HashRouter>,
    document.getElementById('root')
  );
import * as React from 'react'
import * as ReactDOM from "react-dom";

import { App } from "./App"
import "./style.css"

let data = {
    token:""
}
ReactDOM.render(<App data={data} />, document.getElementById('app'));
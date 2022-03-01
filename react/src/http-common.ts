import axios from "axios";
export default axios.create({
  baseURL: "https://cse216-group4-app.herokuapp.com/api/",
  headers: {
    "Content-type": "application/json"
  }
});
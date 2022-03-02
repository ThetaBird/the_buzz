import * as React from "react";
import axios from "axios";
//import fetch from "node-fetch";
//import React, { useState, useEffect } from "react";
import { useQuery, useMutation } from "react-query";
import http from "../http-common";
import IdeaData from "../types/idea.type";

const apiClient = axios.create({
  baseURL: "https://cse216-group4-app.herokuapp.com/api",
  //baseURL: "https://localhost:8080/api",
  headers: {
    "Content-type": "application/json",
  },
});

/*const getAll = fetch("https://cse216-group4-app.herokuapp.com/api/ideas")
    .then(res => res.json())
    .then(json => console.log(json));

  const findbyId = fetch("https://cse216-group4-app.herokuapp.com/api/ideas/" + ideaId)
    .then(res => res.json())
    .then(json => console.log(json));*/

/*const response = await apiClient.get<IdeaData[]>("/ideas");
    return response.data;*/

/*static findById = async (ideaId: string) => {
    const response = await apiClient.get<IdeaData>(`/ideas/${ideaId}`);
    return response.data;*/
  /*static create = async ({ ideaId, subject, content, attachment, allowedRoles }: IdeaData) => {
    const response = await apiClient.post<any>("/ideas", { subject, content, attachment, allowedRoles });
    return response.data;
  }*/

  /*const requestOptionsPost = {
    method: 'POST',
    mode: 'cors',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify('../public/ideas/json')
  };
  const responsePost = await fetch('https://cse216-group4-app.herokuapp.com/api/ideas', requestOptionsPost);
  const create = await responsePost.json();
}*/

  /*async function create(url = 'https://cse216-group4-app.herokuapp.com/api/ideas', data = {ideaId, subject, content, attachment, allowedRoles}) {
    // Default options are marked with *
    const response = await fetch(url, {
      method: 'POST', // *GET, POST, PUT, DELETE, etc.
      mode: 'cors', // no-cors, *cors, same-origin
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      credentials: 'same-origin', // include, *same-origin, omit
      headers: {
        'Content-Type': 'application/json'
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      redirect: 'follow', // manual, *follow, error
      referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
      body: JSON.stringify(data) // body data type must match "Content-Type" header
    })
    .then(response => response.json())
    .then(data => {
      console.log('Success:', data);
    })
    .catch((error) => {
      console.error('Error:', error);
    });
    //return response.json(); // parses JSON response into native JavaScript objects
  }
  
  create('https://cse216-group4-app.herokuapp.com/api/ideas', { IdeaData: { ideaId, subject, content, attachment, allowedRoles } })
    .then(data => {
      console.log(data); // JSON data parsed by `data.json()` call
    });*/
  //static create: any;
  //static findById: any;
  //static getAll: any;

  const getAll = async () => {
    const response = await apiClient.get<IdeaData[]>("/ideas");
    return response.data;
  }
  const findById = async (ideaId: any) => {
    const response = await apiClient.get<IdeaData>(`/ideas/${ideaId}`);
    return response.data;
  }
  const create = async ({ subject, content, attachment, allowedRoles }: IdeaData) => {
    const response = await apiClient.post<IdeaData>("/ideas", { subject, content, attachment, allowedRoles });
    return response.data;
  }

const IdeaService = {
  getAll,
  findById,
  create
}

export default IdeaService;



/*const defaultPosts:IIdea[] = [];
  const IdeaService = () => {
    const [posts, setIdeas]: [IIdea[], (posts: IIdea[]) => void] = React.useState(defaultPosts);
    const [loading, setLoading]: [boolean, (loading: boolean) => void] = React.useState<boolean>(true);
    const [error, setError]: [string, (error: string) => void] = React.useState("");
    React.useEffect(() => {
      axios
        .get<IIdea[]>("https://cse216-group4-app.herokuapp.com/api/ideas");
          headers: {
            "Content-Type": "application/json"
          },
        .then(response => {
          setIdeas(response.data);
          setLoading(false);
        });
        .catch(ex => {
          const error =
          ex.response.status === 404
            ? "Resource Not found"
            : "An unexpected error has occurred";
          setError(error);
          setLoading(false);
        });
  }, []); 


  }*/
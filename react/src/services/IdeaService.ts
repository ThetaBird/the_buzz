import * as React from "react";
import axios from "axios";
//import React, { useState, useEffect } from "react";
import { useQuery, useMutation } from "react-query";
import http from "../http-common";
import Idea from "../types/idea.type";

const apiClient = axios.create({
  baseURL: "https://cse216-group4-app.herokuapp.com/api",
  headers: {
    "Content-type": "application/json",
  },
});

  const findAll = async () => {
    const response = await apiClient.get<Idea[]>("/ideas");
    return response.data;
  }
  const findById = async (id: any) => {
    const response = await apiClient.get<Idea>(`/ideas/${id}`);
    return response.data;
  }
  const create = async ({ userId, subject, content, attachment, allowedRoles }: Idea) => {
    const response = await apiClient.post<any>("/ideas", { subject, content });
    return response.data;
  }
  

  /*const deleteById = async (id: any) => {
    const response = await apiClient.delete<any>(`/ideas/${id}`);
    return response.data;
  }
  const deleteAll = async () => {
    const response = await apiClient.delete<any>("/ideas");
    return response.data;
  }*/

  const IdeaService = {
    findAll,
    findById,
    create,
  }
  export default IdeaService;
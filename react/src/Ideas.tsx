import * as React from 'react'
import ReactDOM from 'react-dom';
//import App from './App';

import { QueryClient, QueryClientProvider } from "react-query";
const queryClient = new QueryClient();
ReactDOM.render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      
    </QueryClientProvider>
  </React.StrictMode>,
  document.getElementById('app')
);
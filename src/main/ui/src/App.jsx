import React, { useEffect, useState } from "react";
import AuthPage from './components/AuthPage.jsx';
import RegPage from './components/RegPage.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import MainPage from "./components/MainPage.jsx";
import ChessPage from "./components/ChessPage.jsx";
import LoadingScreen from "./components/MainPage/LoadingScreen";
import axios from "axios";
import { useHttp } from "./hooks/useEndpoints";


// TODO реализовать навигацию между страницами
const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const apiBaseUrl = useHttp();

  if (isLoading) {
    return <LoadingScreen />;
  }

  return (
      <Router>
        <Routes>
          <Route
              path="/"
              element={isAuthenticated ? <MainPage/> : <AuthPage/>}
          />
          <Route path="/registration" element={<RegPage />} />
          <Route path="/mainpage" element=<MainPage />  />
          <Route path="/chess" element= <ChessPage />  />
        </Routes>
      </Router>
  );
}

export default App;
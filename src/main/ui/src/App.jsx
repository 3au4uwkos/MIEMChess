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
  const [isLoading, setIsLoading] = useState(true);
  const apiBaseUrl = useHttp();

  useEffect(() => {
    const checkAuth = async () => {
      const token = localStorage.getItem('authToken');
      if (!token) {
        setIsLoading(false);
        return;
      }

      try {
        const response = await axios.post(
            `http://localhost:8080/api/validate`,
            {
                'token': `Bearer ${token}`
            }
        );

        if (response.status === 200) {
          setIsAuthenticated(true);
        }
      } catch (error) {
        console.error("Ошибка проверки токена:", error);
        localStorage.removeItem('authToken');
      } finally {
        setIsLoading(false);
      }
    };

    checkAuth()
  }, [apiBaseUrl]);

  if (isLoading) {
    return <LoadingScreen />;
  }

  return (
      <Router>
        <Routes>
          <Route
              path="/"
              element={isAuthenticated ? <MainPage /> : <AuthPage setIsAuthenticated={setIsAuthenticated} />}
          />
          <Route path="/registration" element={<RegPage />} />
          <Route path="/mainpage" element={isAuthenticated ? <MainPage /> : <Navigate to="/" />} />
          <Route path="/chess" element={isAuthenticated ? <ChessPage /> : <Navigate to="/" />} />
        </Routes>
      </Router>
  );
}

export default App;
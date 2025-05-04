import React from "react";
import AuthPage from './components/AuthPage.jsx';
import RegPage from './components/RegPage.jsx';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./components/MainPage.jsx";

const App = () => {
  return (
      <div className="App">
          <MainPage />
      </div>
      /*<div className="MainPage">
      <Router>
          <Routes>
              <Route path="/" element={<AuthPage />} />
              <Route path="/Registration" element={<RegPage />} />
          </Routes>
      </Router>
      </div>
*/
  );
}

export default App;

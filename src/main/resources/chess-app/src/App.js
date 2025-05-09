import React from "react";
import AuthPage from './components/AuthPage.jsx';
import RegPage from './components/RegPage.jsx';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./components/MainPage.jsx";
import ChessPage from "./components/ChessPage.jsx";

const App = () => {
  return (
      <ChessPage />


  );
}

export default App;


/*

      <Router>
          <Routes>
              <Route path="/" element={<AuthPage />} />
              <Route path="/Registration" element={<RegPage />} />
          </Routes>
      </Router>
      */

/*

      <div className="App">
          <ChessPage />
      </div>
      */

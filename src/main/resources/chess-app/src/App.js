import React from "react";
import AuthPage from './components/AuthPage.jsx';
import RegPage from './components/RegPage.jsx';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/" element={<AuthPage />} />
              <Route path="/Registration" element={<RegPage />} />
          </Routes>
      </Router>
  );
}

export default App;

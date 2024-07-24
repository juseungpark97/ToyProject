import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ServiceList from './components/ServiceList';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<ServiceList />} />
      </Routes>
    </Router>
  );
}

export default App;

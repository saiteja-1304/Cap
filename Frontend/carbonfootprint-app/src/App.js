import './App.css';
import React from 'react';
import HomePage from './components/HomePage';
import CarbonTracker from './components/CarbonTracker';
import AuthPage from './components/AuthPage';        
import CommunityPage from './components/CommunityPage';
import LocationPage from './components/LocationPage';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
  return (
    <div>
      <BrowserRouter>
        <div className="container mx-auto">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/carbontracker" element={<CarbonTracker />} />
            <Route path="/community" element={<CommunityPage />} />
            <Route path="/authpage" element={<AuthPage />} />  {/* Route for Register page */}
            <Route path="/location" element={<LocationPage />}/>
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;

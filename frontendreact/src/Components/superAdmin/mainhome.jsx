import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Mainhome.css'; // Import the separate CSS file

export default function Mainhome() {
  const navigate = useNavigate();

  return (
    <div className="fade-in" style={{ textAlign: 'center', padding: '20px' }}>
      <h1 style={{ color: '#333', marginBottom: '30px' }}>Welcome SuperAdmin</h1>
      <div className="button-container">
        <button
          className="navigate-button customer"
          onClick={() => navigate('/home2')}
        >
          Customer Management
        </button>
        <button
          className="navigate-button reports"
          onClick={() => navigate('/home')}
        >
          Reports Dashboard
        </button>
        <button
          className="navigate-button ticket"
          onClick={() => navigate('/home3')}
        >
          Ticket Management
        </button>
        <button
          className="navigate-button opportunity"
          onClick={() => navigate('/home4')}
        >
          Opportunity Management
        </button>
        <button
          className="navigate-button campaign"
          onClick={() => navigate('/home5')}
        >
          Campaign Management
        </button>
      </div>
    </div>
  );
}

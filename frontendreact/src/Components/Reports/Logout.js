import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Logout.css';

function Logout() {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Clear JWT token and other user-related data from localStorage
        localStorage.removeItem('token');
        localStorage.removeItem('role');
       

        // Notify other components of logout
        window.dispatchEvent(new Event('storage'));

        // Redirect to login page after logout
        navigate('/');
    };

    return (
        <div className="logout-page-center">
            <h1>Are you sure you want to logout?</h1>
            <button className="logout-button" onClick={handleLogout}>Logout</button>
        </div>
    );
}

export default Logout;
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { TbLogout } from 'react-icons/tb'; // Logout icon
import { AiOutlineHome } from 'react-icons/ai'; // Home icon
import styles from './Header.module.css';

function Header() {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear authentication tokens or session data
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    window.dispatchEvent(new Event('storage')); // Notify other components about the change
    navigate('/'); // Redirect to the login page
  };

  const handleGoToMainHome = () => {
    navigate('/mainhome'); // Navigate to the main home page
  };

  return (
    <header className={styles.header}>
      <div className={styles.logoHeader}>
        <img src="/favicon.ico" alt="Favicon" className={styles.favicon} />
        <h1>Customer Relationship Management</h1>
      </div>
      <div className={styles.buttonContainer}>
        <button className={styles.homeButton} onClick={handleGoToMainHome}>
          <AiOutlineHome style={{ marginRight: '8px' }} /> Home
        </button>
        <button className={styles.logoutButton} onClick={handleLogout}>
          <TbLogout style={{ marginRight: '8px' }} /> Logout
        </button>
      </div>
    </header>
  );
}

export default Header;

import React, { useState, useRef } from 'react'; // Import useRef
import './Loginform.css';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaLock } from 'react-icons/fa';
import axios from 'axios';

const Loginform = () => {
  const navigate = useNavigate();
  const usernameRef = useRef(null); // Create a ref for the username input
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [showPassword, setShowPassword] = useState(false); // Toggle password visibility

  const handleForgotPassword = (e) => {
    e.preventDefault();
    navigate('/update_password');
  };

  const handleRegister = (e) => {
    e.preventDefault();
    navigate('/register');
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      // Access the value of the username input via the ref
      const username = usernameRef.current.value;

      const response = await axios.post('http://localhost:8084/admin/login', {
        adminName: username,
        adminPassword: password,
      });

      const { token, role } = response.data; // Extract token and role from the response

      if (!token || !role) {
        throw new Error('Token or role not received from backend');
      }

      // Save token and role to localStorage
      localStorage.setItem('token', token);
      localStorage.setItem('role', role);

      // Notify other components of token storage
      window.dispatchEvent(new Event('storage'));

      // Redirect based on role
      if (role === 'SuperAdmin') {
        navigate('/mainhome');
      } else if (role === 'CustomerAdmin') {
        navigate('/home2');
      } 
      else if(role==='SupportAdmin'){
        navigate('/home3');

      }
      else if(role==='SalesAdmin'){
        navigate('/home4');

      }
      else if(role==='CampaignAdmin')
      {
        navigate('/home5');
      }
        else {
        navigate('/home');
      }
    } catch (error) {
      console.error('Login failed:', error.response?.data || error.message);
      setErrorMessage( 'Invalid username or password');
    }
  };

  return (
    <div className="login-page">
      <div className="wrapper">
        <div className="logoHeader">
          <img src="/favicon.ico" alt="Favicon" className="favicon" />
          <h1>Login</h1>
        </div>
        {errorMessage && <p style={{ color: '#FF7276', fontWeight: 'bold',textAlign:'center' }}>{errorMessage}</p>}
        <form onSubmit={handleLogin}>
          <div className="input-box">
            <input
              type="text"
              placeholder="Username"
              required
              ref={usernameRef} // Assign ref to this input
              style={{
                width: '100%',
                height: '100%',
                background: 'transparent',
                outline: 'none',
                borderRadius: '40px',
                fontSize: '16px',
                color: 'white',
                padding: '20px 45px 20px 20px',
                border: '2px solid rgba(255, 255, 255, 0.2)',
              }}
            />
            <FaUser className="icon" />
          </div>
          <div className="input-box">
            <input
              type={showPassword ? 'text' : 'password'}
              placeholder="Password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <FaLock className="icon" />
            <span
              className="toggle-password"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? (
                <i className="fas fa-eye-slash"></i>
              ) : (
                <i className="fas fa-eye"></i>
              )}
            </span>
          </div>
          <div className="remember-forgot">
            <button onClick={handleForgotPassword} className="link-button">
              Forgot password?
            </button>
          </div>
          <button type="submit" className="bb">
            Login
          </button>
          <div className="register-link">
            <p>
              Don't have an account?{' '}
              <button onClick={handleRegister} className="link-button">
                Register
              </button>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Loginform;

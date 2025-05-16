import React, { useState } from 'react';
import './updatepassword.css';
import { FaUser, FaLock, FaKey } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Update_account = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [secretKey, setSecretKey] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.put('http://localhost:8084/admin/update-password', {
                adminName: username,
                adminPassword: password,
                secretCode: secretKey,
            });
            console.log(response.data);
            alert("Password updated");
            navigate('/'); // Navigate to login page after successful password update
        } catch (error) {
            console.error('Password update failed:', error.response?.data || error.message);
            setErrorMessage(error.response?.data || 'An error occurred');
        }
    };

    return (
        <div className="login-page"> 
        <div className="wrapper">
            <h1>Update Password</h1>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
            <form onSubmit={handleUpdate}>
            <div className="input-box">
                    <input
                    type="text"
                    placeholder="Username"
                    required
                    name="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    style={{
                        width: '100%',
                        height: '100%',
                        background: 'transparent',
                      //  border: 'none',
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
                        type="password"
                        placeholder="Password"
                        required
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <FaLock className="icon" />
                </div>
                <div className="input-box">
                    <input
                        type="password"
                        placeholder="SecretKey"
                        required
                        value={secretKey}
                        onChange={(e) => setSecretKey(e.target.value)}
                    />
                    <FaKey className="icon" />
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
        </div>
    );
};

export default Update_account;
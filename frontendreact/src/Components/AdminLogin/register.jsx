import React, { useState } from 'react';
import './register.css';
import { FaUser, FaLock, FaKey } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Registerform = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [secretKey, setSecretKey] = useState('');
    const [role, setRole] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    // Function to validate password
    const validatePassword = (password) => {
        const hasMinLength = password.length >= 8;
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
        const hasTwoNumbers = (password.match(/\d/g) || []).length >= 2;

        return hasMinLength && hasSpecialChar && hasTwoNumbers;
    };

    const handleRegister = async (e) => {
        e.preventDefault();

        // Frontend password validation
        if (!validatePassword(password)) {
            setErrorMessage("Password must be at least 8 characters long, contain at least one special character, and at least two numbers.");
            return;
        }

        try {
            const response = await axios.post('http://localhost:8084/admin/register', {
                adminName: username,
                adminPassword: password,
                secretCode: secretKey,
                role: role,
            });
            console.log(response.data);
            alert("Registration successful");
            navigate('/'); // Navigate to login page after successful registration
        } catch (error) {
            console.error('Registration failed:', error.response?.data || error.message);
            setErrorMessage(error.response?.data?.error || 'An error occurred');
        }
    };

    return (
        <div className="login-page"> 
            <div className="wrapper">
                <h1>Registration</h1>
                {errorMessage && <p style={{ color: '#FF7276', fontWeight: 'bold' }}>{errorMessage}</p>}
                <form onSubmit={handleRegister}>
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
                    <div className="input-box">
                        <select
                            className="role-dropdown"
                            required
                            value={role}
                            onChange={(e) => setRole(e.target.value)}
                        >
                            <option value="" disabled>
                                Select Role
                            </option>
                            <option value="SuperAdmin">SuperAdmin</option>
                            <option value="CustomerAdmin">CustomerAdmin</option>
                            <option value="SupportAdmin">SupportAdmin</option>
                            <option value="SalesAdmin">SalesAdmin</option>
                            <option value="CampaignAdmin">CampaignAdmin</option>
                            <option value="ReportAdmin">ReportAdmin</option>
                        </select>
                    </div>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    );
};

export default Registerform;

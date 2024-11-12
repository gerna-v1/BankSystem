import React, { useState } from 'react';
import authService from '../services/authService';
import FormContainer from './FormContainer';
import Notification from './Notification';

const LoginForm = ({ userType, onLoginSuccess }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = userType === 'client'
        ? await authService.loginClient({ username, password })
        : await authService.loginAdmin({ username, password });
      onLoginSuccess(response.data);
    } catch (err) {
      setError('Error logging in: ' + err.message);
    }
  };

  const clearError = () => {
    setError('');
  };

  return (
    <FormContainer title="Welcome back!!" description="Login to your account.">
      <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        <button type="submit" className="w-full p-2 bg-blue-500 text-white rounded">
          Login
        </button>
      </form>
      {error && <Notification message={error} type="error" clearNotification={clearError} />}
    </FormContainer>
  );
};

export default LoginForm;
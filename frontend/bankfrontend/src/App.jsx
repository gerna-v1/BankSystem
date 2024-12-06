import React, { useState, useEffect } from 'react';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import Notification from './components/Notification';
import './index.css';
import './output.css';

function App() {
  const [userType, setUserType] = useState('client');
  const [formType, setFormType] = useState('login');
  const [notification, setNotification] = useState({ message: '', type: '' });
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);

  const handleLoginSuccess = (data) => {
    setNotification({ message: 'Login successful!', type: 'success' });
    setIsLoggedIn(true);
  };

  const handleRegisterSuccess = (data) => {
    setNotification({ message: 'Registration successful!', type: 'success' });
  };

  const clearNotification = () => {
    setNotification({ message: '', type: '' });
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    setNotification({ message: 'Logged out successfully!', type: 'success' });
  };

  return (
    <div className="App p-4">
      <div className="flex space-x-4 mb-4">
        <button
          onClick={() => setUserType('client')}
          className={`p-2 rounded ${userType === 'client' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Client
        </button>
        <button
          onClick={() => setUserType('admin')}
          className={`p-2 rounded ${userType === 'admin' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Admin
        </button>
      </div>
      <div className="flex space-x-4 mb-4">
        <button
          onClick={() => setFormType('login')}
          className={`p-2 rounded ${formType === 'login' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Login
        </button>
        <button
          onClick={() => setFormType('register')}
          className={`p-2 rounded ${formType === 'register' ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}
        >
          Register
        </button>
      </div>
      {isLoggedIn && (
        <button onClick={handleLogout} className="p-2 bg-red-500 text-white rounded mb-4">
          Logout
        </button>
      )}
      {formType === 'login' ? (
        <LoginForm userType={userType} onLoginSuccess={handleLoginSuccess} />
      ) : (
        <RegisterForm userType={userType} onRegisterSuccess={handleRegisterSuccess} />
      )}
      <Notification message={notification.message} type={notification.type} clearNotification={clearNotification} />
    </div>
  );
}

export default App;
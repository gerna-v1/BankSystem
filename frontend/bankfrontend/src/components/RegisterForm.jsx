import React, { useState } from 'react';
import authService from '../services/authService';
import FormContainer from './FormContainer';
import Notification from './Notification';

const RegisterForm = ({ userType, onRegisterSuccess }) => {
  const [formData, setFormData] = useState({
    name: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    govId: '',
    phone: '',
    accessLevel: 1,
  });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = userType === 'client'
        ? await authService.registerClient(formData)
        : await authService.registerAdmin(formData);
      onRegisterSuccess(response.data);
    } catch (err) {
      setError('Error registering: ' + err.message);
    }
  };

  const clearError = () => {
    setError('');
  };

  return (
    <FormContainer title="We are pleased to meet you!!" description="Register your account.">
      <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
        <input
          type="text"
          name="name"
          placeholder="Name"
          value={formData.name}
          onChange={handleChange}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        <input
          type="text"
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        <input
          type="text"
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
          className="w-full p-2 border border-gray-300 rounded"
        />
        {userType === 'client' && (
          <>
            <input
              type="text"
              name="govId"
              placeholder="Government ID"
              value={formData.govId}
              onChange={handleChange}
              required
              className="w-full p-2 border border-gray-300 rounded"
            />
            <input
              type="text"
              name="phone"
              placeholder="Phone"
              value={formData.phone}
              onChange={handleChange}
              required
              className="w-full p-2 border border-gray-300 rounded"
            />
          </>
        )}
        <button type="submit" className="w-full p-2 bg-blue-500 text-white rounded">
          Register
        </button>
      </form>
      {error && <Notification message={error} type="error" clearNotification={clearError} />}
    </FormContainer>
  );
};

export default RegisterForm;
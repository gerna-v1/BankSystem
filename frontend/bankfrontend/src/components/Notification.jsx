import React, { useEffect } from 'react';
import './Notification.css';

const Notification = ({ message, type, clearNotification }) => {
  useEffect(() => {
    if (message) {
      const timer = setTimeout(() => {
        clearNotification();
      }, 5000);
      return () => clearTimeout(timer);
    }
  }, [message, clearNotification]);

  if (!message) return null;

  return (
    <div className={`notification ${type === 'success' ? 'bg-green-500' : 'bg-red-500'} text-white p-2 rounded`}>
      {message}
      <div className="notification-progress"></div>
    </div>
  );
};

export default Notification;
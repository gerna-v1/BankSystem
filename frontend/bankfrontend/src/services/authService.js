import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

const apiClient = axios.create({
  baseURL: API_URL,
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const loginClient = async (loginRequest) => {
  const response = await apiClient.post('/login/client', loginRequest);
  localStorage.setItem('token', response.data.data.accessToken);
  return response;
};

const loginAdmin = async (loginRequest) => {
  const response = await apiClient.post('/login/admin', loginRequest);
  localStorage.setItem('token', response.data.data.accessToken);
  return response;
};

const registerClient = (clientRegisterDTO) => {
  return apiClient.post('/register/client', clientRegisterDTO);
};

const registerAdmin = (adminRegisterDTO) => {
  return apiClient.post('/register/admin', adminRegisterDTO);
};

export default {
  loginClient,
  loginAdmin,
  registerClient,
  registerAdmin,
};
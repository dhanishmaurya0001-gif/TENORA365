import axios from 'axios';

const origin = (import.meta.env.VITE_API_URL || '').replace(/\/$/, '');
const api = axios.create({
    baseURL: origin.endsWith('/api') ? origin : `${origin}/api`,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('tenora_access');

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem('tenora_access');
            localStorage.removeItem('tenora_user');
            window.dispatchEvent(new Event('tenora:expired'));
        }

        return Promise.reject(error);
    }
);

export default api;

import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/', // 백엔드 API URL
});

// 요청 인터셉터를 추가하여 모든 요청에 토큰을 추가
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // 로컬 스토리지에서 JWT 토큰 가져오기
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../utils/api'; // API 요청을 위한 axios 인스턴스

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      // 로그인 API 요청
      const response = await api.post('/auth/login', { email, password });
      const token = response.data.token;

      // JWT 토큰을 로컬 스토리지에 저장
      localStorage.setItem('token', token);
      alert('로그인 성공');
      navigate('/'); // 로그인 성공 후 홈 페이지로 이동
    } catch (err: any) {
      setError(err.response?.data?.message || '로그인 중 오류가 발생했습니다.');
    }
  };

  return (
    <div>
      <h1>로그인</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleLogin}>
        <div>
          <label htmlFor="email">이메일:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">로그인</button>
      </form>
    </div>
  );
};

export default LoginPage;
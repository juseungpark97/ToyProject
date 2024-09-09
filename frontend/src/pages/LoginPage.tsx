import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux'; // useDispatch 훅 가져오기
import { login } from '../redux/slices/authSlice'; // login 액션 가져오기
import api from '../utils/api'; // API 요청을 위한 axios 인스턴스

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch(); // useDispatch 훅 사용
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
  
    try {
      // 로그인 API 요청
      const response = await api.post('/auth/login', { email, password });
  
      // 성공적으로 로그인된 경우
      if (response.data.token) {
        const token = response.data.token;
  
        // JWT 토큰을 로컬 스토리지에 저장
        localStorage.setItem('token', token);
        
        // Redux 상태 업데이트: 로그인 액션 디스패치
        dispatch(login());
        
        alert('로그인 성공');
        navigate('/'); // 로그인 성공 후 홈 페이지로 이동
      } else if (response.data.status === 401) {
        // 200 OK이지만 서버에서 오류를 반환한 경우
        setError('이메일이나 비밀번호가 틀렸습니다.');
      }
    } catch (err: any) {
      // 그 외의 오류
      setError(err.response?.data?.error || '로그인 중 오류가 발생했습니다.');
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
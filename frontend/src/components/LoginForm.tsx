import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../redux/store'; // Redux 상태 타입 가져오기
import { login, logout } from '../redux/slices/authSlice'; // 로그인 및 로그아웃 액션 가져오기
import api from '../utils/api';
import styles from './LoginForm.module.css'; // CSS 모듈 가져오기
import logo from '../assets/logo.png'; // 로고 이미지 가져오기

const LoginForm: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const user = useSelector((state: RootState) => state.auth.user); // 로그인된 사용자 정보 가져오기

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
  
    try {
      const response = await api.post('/auth/login', { email, password });
  
      if (response.data.token) {
        const token = response.data.token;
        const role = response.data.role; // 서버 응답에서 역할 정보 추출
        const email = response.data.email; // 서버 응답에서 이메일 정보 추출
  
        // 로컬 스토리지에 저장
        localStorage.setItem('token', token);
        localStorage.setItem('userEmail', email); 
        localStorage.setItem('userRole', role); 
  
        // Redux 상태 업데이트
        dispatch(login({ email, role }));
  
        // 사용자 역할이 "ADMIN"이면 관리자 페이지로 리다이렉트
        if (role?.toLowerCase() === 'admin') {
          navigate('/admin'); 
        }
  
        alert('로그인 성공');
      } else if (response.data.status === 401) {
        setError('이메일이나 비밀번호가 틀렸습니다.');
      }
    } catch (err: any) {
      setError(err.response?.data?.error || '로그인 중 오류가 발생했습니다.');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail'); // 이메일 제거
    dispatch(logout());
  };

  return (
    <div className={styles.page}>
      <img src={logo} alt="Logo" className={styles.logo} />
      {isAuthenticated ? (
        <div className={styles.container}>
          <h1 className={styles.title}>환영합니다, {user?.email || '사용자'}님!</h1>
          <button onClick={handleLogout} className={styles.button}>
            로그아웃
          </button>
        </div>
      ) : (
        <div className={styles.container}>
          <h1 className={styles.title}>로그인</h1>
          {error && <p className={styles.error}>{error}</p>}
          <form onSubmit={handleLogin}>
            <div>
              <label htmlFor="email">이메일:</label>
              <input
                type="email"
                id="email"
                className={styles.inputField}
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
                className={styles.inputField}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className={styles.button}>로그인</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default LoginForm;
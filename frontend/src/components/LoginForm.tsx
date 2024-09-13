import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { login, logout } from '../redux/slices/authSlice';
import api from '../utils/api';
import styles from './LoginForm.module.css';
import logo from '../assets/logo.png';

const LoginForm: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const user = useSelector((state: RootState) => state.auth.user);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await api.post('/auth/login', { email, password });

      if (response.data.token) {
        const token = response.data.token;
        const role = response.data.role;
        const email = response.data.email;

        localStorage.setItem('token', token);
        localStorage.setItem('userEmail', email);
        localStorage.setItem('userRole', role);

        dispatch(login({ email, role }));

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
    localStorage.removeItem('userEmail');
    dispatch(logout());
  };

  // 회원가입 페이지로 이동하는 함수
  const handleSignup = () => {
    navigate('/signup');
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
          {/* 회원가입 버튼 추가 */}
          <button onClick={handleSignup} className={styles.signupButton}>
            회원가입
          </button>
        </div>
      )}
    </div>
  );
};

export default LoginForm;
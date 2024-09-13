// App.tsx
import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate, useLocation } from 'react-router-dom';
import HomePage from './pages/HomePage';
import SignupPage from './pages/SignupPage';
import BookDetailPage from './pages/BookDetailPage';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './redux/store';
import { login } from './redux/slices/authSlice';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { store, persistor } from './redux/store';
import LoginForm from './components/LoginForm';
import MyPage from './pages/MyPage';
import AdminPage from './pages/AdminPage'; // 관리자 페이지 컴포넌트 임포트

// 특정 역할이 있는 사용자만 접근 가능하도록 설정하는 PrivateRoute 컴포넌트
const PrivateRoute: React.FC<{ children: JSX.Element; role?: string }> = ({ children, role }) => {
  const userRole = useSelector((state: RootState) => state.auth.user?.role); // 사용자 역할 가져오기

  if (role && userRole?.toLowerCase() !== role.toLowerCase()) {
    return <Navigate to="/" />; // 역할이 일치하지 않으면 홈으로 리다이렉트
  }

  return children;
};

const App: React.FC = () => {
  const dispatch = useDispatch();

  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <Router>
          <AppRoutes dispatch={dispatch} />
        </Router>
      </PersistGate>
    </Provider>
  );
};

const AppRoutes: React.FC<{ dispatch: any }> = ({ dispatch }) => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const userEmail = localStorage.getItem('userEmail') || undefined;
      const userRole = localStorage.getItem('userRole') || undefined;

      if (userEmail) {
        dispatch(login({ email: userEmail, role: userRole }));

        if (userRole?.toLowerCase() === 'admin' && location.pathname !== '/admin') {
          navigate('/admin');
        }
      }
    }
  }, [dispatch, navigate, location]);

  return (
    <Routes>
      {/* 일반 사용자 페이지 */}
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/signup" element={<SignupPage />} />
      <Route path="/book/:bookId" element={<BookDetailPage />} />
      <Route path="/mypage" element={<PrivateRoute><MyPage /></PrivateRoute>} />

      {/* 관리자 전용 페이지 */}
      <Route path="/admin" element={<PrivateRoute role="admin"><AdminPage /></PrivateRoute>} />
    </Routes>
  );
};

export default App;
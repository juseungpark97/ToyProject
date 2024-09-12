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
import BookUploadPage from './pages/BookUploadPage';
import LoginForm from './components/LoginForm';
import MyPage from './pages/MyPage';
import AdminPage from './pages/AdminPage'; // 관리자 페이지 추가

const PrivateRoute: React.FC<{ children: JSX.Element; role?: string }> = ({ children, role }) => {
  const userRole = useSelector((state: RootState) => state.auth.user?.role); // 사용자 역할 가져오기
  console.log(`PrivateRoute - Required Role: ${role}, Current Role: ${userRole}`); // 역할 확인

  if (role && userRole?.toLowerCase() !== role.toLowerCase()) {
    console.log(`접근 불가: 필요한 역할은 ${role}, 현재 역할은 ${userRole}`);
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
  const location = useLocation(); // 현재 위치 가져오기

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const userEmail = localStorage.getItem('userEmail') || undefined;
      const userRole = localStorage.getItem('userRole') || undefined; // 로컬 스토리지에서 역할 가져오기

      if (userEmail) {
        dispatch(login({ email: userEmail, role: userRole }));

        // "ADMIN" 역할이고 현재 페이지가 '/admin' 또는 '/upload'가 아닌 경우에만 관리자 페이지로 이동
        if (userRole?.toLowerCase() === 'admin' && location.pathname !== '/admin' && location.pathname !== '/upload') {
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
      <Route path="/upload" element={<PrivateRoute role="admin"><BookUploadPage /></PrivateRoute>} /> 
    </Routes>
  );
};

export default App;
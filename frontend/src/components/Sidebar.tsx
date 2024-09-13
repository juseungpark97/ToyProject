// Sidebar.tsx
import React from 'react';
import { useDispatch } from 'react-redux'; // Redux dispatch 훅 가져오기
import { useNavigate } from 'react-router-dom'; // useNavigate 훅 가져오기
import { logout } from '../redux/slices/authSlice'; // 로그아웃 액션 가져오기

interface SidebarProps {
  setActiveTab: (tab: string) => void;
}

const Sidebar: React.FC<SidebarProps> = ({ setActiveTab }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    // 로그아웃 처리
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userRole');
    dispatch(logout()); // Redux 상태에서 로그아웃 처리
    navigate('/'); // 홈 페이지로 리다이렉트
  };

  return (
    <div style={{ width: '200px', background: '#f0f0f0', padding: '20px' }}>
      <h2>관리자 메뉴</h2>
      <ul style={{ listStyleType: 'none', padding: 0 }}>
        <li style={{ marginBottom: '10px' }}>
          <button onClick={() => setActiveTab('userManagement')}>
            유저 관리
          </button>
        </li>
        <li style={{ marginBottom: '10px' }}>
          <button onClick={() => setActiveTab('bookUpload')}>
            도서 업로드
          </button>
        </li>
        <li style={{ marginBottom: '10px' }}>
          <button onClick={() => setActiveTab('bookManagement')}>
            도서 관리
          </button>
        </li>
        <li style={{ marginBottom: '10px' }}>
          <button onClick={handleLogout}>
            로그아웃
          </button>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
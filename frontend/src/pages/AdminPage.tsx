import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; // useNavigate를 사용하기 위해 추가
import api from '../utils/api';
import { useDispatch } from 'react-redux';
import { logout } from '../redux/slices/authSlice';

interface User {
  userId: number; // 'id'를 'userId'로 변경
  name: string;
  email: string;
}

interface Rental {
  rentalId: number;
  title: string;
  author: string;
  rentalDate: string;
  returnDate: string | null;
}

const AdminPage: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [rentals, setRentals] = useState<Rental[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const navigate = useNavigate(); // useNavigate 훅 사용
  const dispatch = useDispatch();

  useEffect(() => {
    api.get('/api/users')
      .then((response) => {
        setUsers(response.data);
        console.log('Users loaded:', response.data); // 유저 목록 로드 확인
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  }, []);

  const handleUserClick = (userId: number) => {
    console.log('Selected user ID:', userId); // 클릭된 유저 ID 확인
    api.get(`/api/users/${userId}`)
      .then((response) => {
        setSelectedUser(response.data);
        console.log('User details:', response.data); // 선택된 유저 정보 확인
      })
      .catch((error) => {
        console.error('Error fetching user details:', error);
      });

    fetchRentals(userId, 0); // 초기 페이지 0으로 대여 기록 조회
  };

  const fetchRentals = (userId: number, page: number) => {
    console.log('Fetching rentals for user ID:', userId, 'Page:', page); // 대여 기록 조회 요청 확인
    api.get(`/api/users/${userId}/rentals`, { params: { page, size: 5 } })
      .then((response) => {
        setRentals(response.data.content); // 현재 페이지의 대여 기록 설정
        setCurrentPage(response.data.number); // 현재 페이지 번호 설정
        setTotalPages(response.data.totalPages); // 전체 페이지 수 설정
        console.log('Rentals fetched:', response.data); // 대여 기록 확인
      })
      .catch((error) => {
        console.error('Error fetching rentals:', error);
      });
  };

  const handleNextPage = () => {
    if (selectedUser && currentPage < totalPages - 1) {
      fetchRentals(selectedUser.userId, currentPage + 1); // 'id' 대신 'userId' 사용
    }
  };

  const handlePreviousPage = () => {
    if (selectedUser && currentPage > 0) {
      fetchRentals(selectedUser.userId, currentPage - 1); // 'id' 대신 'userId' 사용
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token'); // 토큰 제거
    localStorage.removeItem('userEmail'); // 사용자 이메일 제거
    localStorage.removeItem('userRole');
    dispatch(logout()); // 로그아웃 액션 호출
    navigate('/');
  };

  const handleNavigateToUpload = () => {
    navigate('/upload'); // 업로드 페이지로 이동
  };

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>관리자 페이지</h1>
        <div>
          <button onClick={handleLogout} style={{ marginRight: '10px' }}>로그아웃</button>
          <button onClick={handleNavigateToUpload}>업로드 페이지로 이동</button>
        </div>
      </div>
      
      <h2>유저 리스트</h2>
      <ul>
        {users.map((user) => (
          <li key={user.userId} onClick={() => handleUserClick(user.userId)}> {/* 'id' 대신 'userId' 사용 */}
            {user.name} ({user.email})
          </li>
        ))}
      </ul>

      {selectedUser && (
        <>
          <h2>유저 상세 정보</h2>
          <p>이름: {selectedUser.name}</p>
          <p>이메일: {selectedUser.email}</p>

          <h3>대여 기록 (페이지 {currentPage + 1} / {totalPages})</h3>
          <ul>
            {rentals.map((rental) => (
              <li key={`rental-${rental.rentalId}`}> {/* 고유한 키 추가 */}
                <h4>{rental.title}</h4>
                <p>저자: {rental.author}</p>
                <p>대여일: {new Date(rental.rentalDate).toLocaleDateString()}</p>
                {rental.returnDate ? (
                  <p>반납일: {new Date(rental.returnDate).toLocaleDateString()}</p>
                ) : (
                  <p>반납되지 않음</p>
                )}
              </li>
            ))}
          </ul>

          <button onClick={handlePreviousPage} disabled={currentPage === 0}>
            이전
          </button>
          <button onClick={handleNextPage} disabled={currentPage >= totalPages - 1}>
            다음
          </button>
        </>
      )}
    </div>
  );
};

export default AdminPage;
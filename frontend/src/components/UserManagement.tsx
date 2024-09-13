// UserManagement.tsx
import React, { useEffect, useState } from 'react';
import api from '../utils/api';

interface User {
  userId: number;
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

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const [rentals, setRentals] = useState<Rental[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    api.get('/api/users')
      .then((response) => {
        setUsers(response.data);
      })
      .catch((error) => {
        console.error('Error fetching users:', error);
      });
  }, []);

  const handleUserClick = (userId: number) => {
    api.get(`/api/users/${userId}`)
      .then((response) => {
        setSelectedUser(response.data);
      })
      .catch((error) => {
        console.error('Error fetching user details:', error);
      });

    fetchRentals(userId, 0);
  };

  const fetchRentals = (userId: number, page: number) => {
    api.get(`/api/users/${userId}/rentals`, { params: { page, size: 5 } })
      .then((response) => {
        setRentals(response.data.content);
        setCurrentPage(response.data.number);
        setTotalPages(response.data.totalPages);
      })
      .catch((error) => {
        console.error('Error fetching rentals:', error);
      });
  };

  const handleNextPage = () => {
    if (selectedUser && currentPage < totalPages - 1) {
      fetchRentals(selectedUser.userId, currentPage + 1);
    }
  };

  const handlePreviousPage = () => {
    if (selectedUser && currentPage > 0) {
      fetchRentals(selectedUser.userId, currentPage - 1);
    }
  };

  return (
    <div>
      <h2>유저 관리</h2>
      <ul>
        {users.map((user) => (
          <li key={user.userId} onClick={() => handleUserClick(user.userId)}>
            {user.name} ({user.email})
          </li>
        ))}
      </ul>

      {selectedUser && (
        <div>
          <h3>유저 상세 정보</h3>
          <p>이름: {selectedUser.name}</p>
          <p>이메일: {selectedUser.email}</p>

          <h3>대여 기록 (페이지 {currentPage + 1} / {totalPages})</h3>
          <ul>
            {rentals.map((rental) => (
              <li key={`rental-${rental.rentalId}`}>
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
        </div>
      )}
    </div>
  );
};

export default UserManagement;
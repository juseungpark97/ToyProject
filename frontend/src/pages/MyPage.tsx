import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import api from '../utils/api';

interface Rental {
  rentalId: number;
  title: string;
  author: string;
  rentalDate: string;
  returnDate: string | null;
}

const MyPage: React.FC = () => {
  const [rentals, setRentals] = useState<Rental[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated); // 인증 상태 확인

  useEffect(() => {
    if (isAuthenticated) {
      fetchRentals(0); // 초기 페이지 0으로 대여 기록 조회
    }
  }, [isAuthenticated]);

  const fetchRentals = (page: number) => {
    api.get(`/api/rentals/my`, { params: { page, size: 5 } }).then((response) => {
      setRentals(response.data.content); // 현재 페이지의 대여 기록 설정
      setCurrentPage(response.data.number); // 현재 페이지 번호 설정
      setTotalPages(response.data.totalPages); // 전체 페이지 수 설정
    }).catch((error) => {
      console.error('대여 기록을 가져오는 중 오류 발생:', error);
    });
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      fetchRentals(currentPage + 1);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      fetchRentals(currentPage - 1);
    }
  };

  if (!isAuthenticated) {
    return <p>로그인 후 이용 가능합니다.</p>;
  }

  return (
    <div>
      <h1>나의 대여 기록</h1>
      {rentals.length > 0 ? (
        <>
          <ul>
            {rentals.map((rental) => (
              <li key={rental.rentalId}>
                <h2>{rental.title}</h2>
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
      ) : (
        <p>대여 기록이 없습니다.</p>
      )}
    </div>
  );
};

export default MyPage;
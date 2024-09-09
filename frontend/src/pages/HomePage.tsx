import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { useNavigate } from 'react-router-dom';
import api from '../utils/api'; // Axios 인스턴스 import

interface Book {
  bookId: number;
  title: string;
  author: string;
  publicationDate: string;
  genre: string;
  stockQuantity: number;
  imageUrl: string;
  category: {
    name: string;
  };
}

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const [books, setBooks] = useState<Book[]>([]);
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

  useEffect(() => {
    if (!isAuthenticated) {
      alert('로그인이 필요합니다.');
      navigate('/login');
    } else {
      // 로그인되어 있는 경우 서버에서 책 정보를 가져옵니다.
      api
        .get('/api/books')
        .then((response) => {
          setBooks(response.data); // 가져온 데이터를 상태에 저장
        })
        .catch((error) => {
          console.error('Error fetching books:', error);
        });
    }
  }, [isAuthenticated, navigate]);

  const handleBookClick = (bookId: number) => {
    navigate(`/book/${bookId}`); // 상세 페이지로 이동
  };

  return (
    <div>
      <h1>홈페이지</h1>
      {isAuthenticated ? (
        <>
          <h2>도서 목록</h2>
          <div style={{ display: 'flex', flexWrap: 'wrap' }}>
            {books.map((book) => (
              <div
                key={book.bookId}
                style={{ margin: '20px', border: '1px solid #ddd', padding: '10px', width: '200px', cursor: 'pointer' }}
                onClick={() => handleBookClick(book.bookId)} // 클릭 이벤트 추가
              >
                {book.imageUrl ? (
                  <img src={book.imageUrl} alt={book.title} style={{ width: '100%', height: 'auto' }} />
                ) : (
                  <div style={{ width: '100%', height: '200px', backgroundColor: '#f0f0f0' }}>No Image</div>
                )}
                <h3>{book.title}</h3>
              </div>
            ))}
          </div>
        </>
      ) : (
        <p>로그인 중입니다...</p>
      )}
    </div>
  );
};

export default HomePage;
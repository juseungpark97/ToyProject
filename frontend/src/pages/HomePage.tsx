import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../redux/store';
import { useNavigate } from 'react-router-dom';
import api from '../utils/api';
import LoginForm from '../components/LoginForm';
import { logout } from '../redux/slices/authSlice'; // 로그아웃 액션 가져오기
import styles from './HomePage.module.css'; // CSS 모듈 가져오기

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
  const dispatch = useDispatch();
  const [books, setBooks] = useState<Book[]>([]);
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated); // 로그인 상태
  const user = useSelector((state: RootState) => state.auth.user); // 사용자 정보 가져오기

  useEffect(() => {
    // 서버에서 책 정보를 가져옵니다.
    api
      .get('/api/books')
      .then((response) => {
        setBooks(response.data); // 가져온 데이터를 상태에 저장
      })
      .catch((error) => {
        console.error('Error fetching books:', error);
      });
  }, []);

  const handleBookClick = (bookId: number) => {
    navigate(`/book/${bookId}`); // 상세 페이지로 이동
  };

  const handleLogout = () => {
    localStorage.removeItem('token'); // 토큰 제거
    localStorage.removeItem('userEmail'); // 사용자 이메일 제거
    dispatch(logout()); // 로그아웃 액션 호출
  };

  const handleMyPage = () => {
    navigate('/mypage');
  }

  return (
    <div className={styles.container}>
      {/* 헤더 섹션 */}
      <header className={styles.header}>
        <div className={styles.logo}>Book Forum</div>
        <div className={styles.searchContainer}>
          <input type="text" className={styles.searchInput} placeholder="Search..." />
          <button className={styles.searchButton}>Q</button>
        </div>
      </header>

      <div className={styles.content}>
        {/* 도서 목록 섹션 */}
        <main className={styles.main}>
          <h2>도서 목록</h2>
          <div className={styles.bookGrid}>
            {books.map((book) => (
              <div
                key={book.bookId}
                className={styles.bookCard}
                onClick={() => handleBookClick(book.bookId)}
              >
                {book.imageUrl ? (
                  <img src={book.imageUrl} alt={book.title} className={styles.bookImage} />
                ) : (
                  <div className={styles.noImage}>No Image</div>
                )}
                <h3>{book.title}</h3>
              </div>
            ))}
          </div>
        </main>

        {/* 로그인 섹션 */}
        <aside className={styles.sidebar}>
          {isAuthenticated ? (
            <div>
              <p>환영합니다, {user?.email || '사용자'}님!</p>
              <button onClick={handleLogout} className={styles.button}>
                로그아웃
              </button>
              <button onClick={handleMyPage} className={styles.button}>
                마이페이지
              </button>
            </div>
          ) : (
            <LoginForm />
          )}
        </aside>
      </div>
    </div>
  );
};

export default HomePage;
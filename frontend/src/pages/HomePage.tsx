import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../redux/store';
import { useNavigate } from 'react-router-dom';
import api from '../utils/api';
import LoginForm from '../components/LoginForm';
import { logout } from '../redux/slices/authSlice'; 
import styles from './HomePage.module.css'; 

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
  const [searchTerm, setSearchTerm] = useState<string>(''); // 검색어 상태 추가
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const user = useSelector((state: RootState) => state.auth.user);

  useEffect(() => {
    fetchBooks(); // 컴포넌트가 처음 렌더링될 때 책 목록을 가져옴
  }, []);

  const fetchBooks = (title: string = '') => {
    const endpoint = title ? `/api/books/search?title=${title}` : '/api/books';
    api
      .get(endpoint)
      .then((response) => {
        setBooks(response.data);
      })
      .catch((error) => {
        console.error('Error fetching books:', error);
      });
  };

  const handleBookClick = (bookId: number) => {
    navigate(`/book/${bookId}`);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    dispatch(logout());
  };

  const handleMyPage = () => {
    navigate('/mypage');
  };

  const handleSearch = () => {
    fetchBooks(searchTerm); // 검색 버튼 클릭 시 검색어에 따라 책 목록을 가져옴
  };
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch(); // 엔터 키를 눌렀을 때 검색 실행
    }
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <div className={styles.logo}>Book Forum</div>
        <div className={styles.searchContainer}>
          <input
            type="text"
            className={styles.searchInput}
            placeholder="Search..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyDown={handleKeyDown}
             // 검색어 입력 상태 업데이트
          />
          <button onClick={handleSearch} className={styles.searchButton}>Q</button>
        </div>
      </header>

      <div className={styles.content}>
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
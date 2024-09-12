import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store'; // RootState 타입을 사용하여 전체 상태의 타입을 가져옵니다.
import api from '../utils/api';

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

const BookDetailPage: React.FC = () => {
  const { bookId } = useParams<{ bookId: string }>();
  const [book, setBook] = useState<Book | null>(null);
  const [isRented, setIsRented] = useState<boolean>(false);
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated); // Redux 상태에서 인증 여부 가져오기

  const fetchBookDetails = () => {
    api.get(`/api/books/${bookId}`).then((response) => {
      setBook(response.data);
    });
  };

  useEffect(() => {
    fetchBookDetails();
    api.post('/api/rentals/check', { bookId: Number(bookId) }).then((response) => {
      setIsRented(response.data);
    });
  }, [bookId]);

  const handleRentBook = async () => {
    try {
      await api.post('/api/rentals/rent', { bookId: Number(bookId) });
      alert('도서 대여에 성공했습니다!');
      setIsRented(true);
      fetchBookDetails();
    } catch (err) {
      console.error('도서 대여 중 오류 발생:', err);
    }
  };

  const handleReturnBook = async () => {
    try {
      await api.post('/api/rentals/return', { bookId: Number(bookId) });
      alert('도서 반납에 성공했습니다!');
      setIsRented(false);
      fetchBookDetails();
    } catch (err) {
      console.error('도서 반납 중 오류 발생:', err);
    }
  };

  return (
    <div>
      {book ? (
        <>
          <h1>{book.title}</h1>
          {book.imageUrl && <img src={book.imageUrl} alt={book.title} style={{ width: '300px', height: 'auto' }} />}
          <p>저자: {book.author}</p>
          <p>출판일: {new Date(book.publicationDate).toLocaleDateString()}</p>
          <p>장르: {book.genre}</p>
          <p>재고 수량: {book.stockQuantity}</p>
          <p>카테고리: {book.category.name}</p>
          {!isRented ? (
            <button onClick={handleRentBook} disabled={!isAuthenticated}>
              대여하기
            </button>
          ) : (
            <button onClick={handleReturnBook}>반납하기</button>
          )}
        </>
      ) : (
        <p>도서 정보를 찾을 수 없습니다.</p>
      )}
    </div>
  );
};

export default BookDetailPage;
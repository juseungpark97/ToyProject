import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
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

  const fetchBookDetails = () => {
    api.get(`/api/books/${bookId}`).then((response) => {
      setBook(response.data);
    });
  };

  useEffect(() => {
    // 도서 정보 가져오기
    fetchBookDetails();

    // 대여 상태 확인 API 호출
    api.post('/api/rentals/check', { bookId: Number(bookId) }).then((response) => {
      setIsRented(response.data); // 대여 상태 설정
    });
  }, [bookId]);

  const handleRentBook = async () => {
    try {
      await api.post('/api/rentals/rent', { bookId: Number(bookId) });
      alert('도서 대여에 성공했습니다!');
      setIsRented(true); // 대여 상태 변경
      fetchBookDetails(); // 도서 정보 다시 가져오기
    } catch (err) {
      console.error('도서 대여 중 오류 발생:', err);
    }
  };

  const handleReturnBook = async () => {
    try {
      await api.post('/api/rentals/return', { bookId: Number(bookId) });
      alert('도서 반납에 성공했습니다!');
      setIsRented(false); // 대여 상태 변경
      fetchBookDetails(); // 도서 정보 다시 가져오기
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
            <button onClick={handleRentBook}>대여하기</button> // 대여하기 버튼
          ) : (
            <button onClick={handleReturnBook}>반납하기</button> // 반납하기 버튼
          )}
        </>
      ) : (
        <p>도서 정보를 찾을 수 없습니다.</p>
      )}
    </div>
  );
};

export default BookDetailPage;
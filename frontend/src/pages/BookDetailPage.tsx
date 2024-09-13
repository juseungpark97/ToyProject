import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import api from '../utils/api';

// ReviewDTO 인터페이스 정의
interface ReviewDTO {
  reviewId?: number;
  name: string; // 작성자 이름
  creationDate?: string; // 작성일자
  rating: number; // 평점
  content: string; // 리뷰 내용
}

interface Book {
  bookId: number;
  title: string;
  author: string;
  publicationDate: string;
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
  const [reviews, setReviews] = useState<ReviewDTO[]>([]);
  const [hasReviewed, setHasReviewed] = useState<boolean>(false);
  const [newReview, setNewReview] = useState<ReviewDTO>({
    name: '', // 초기값 설정
    rating: 5,
    content: '',
  });

  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  const userEmail = useSelector((state: RootState) => state.auth.user?.email);

  useEffect(() => {
    if (userEmail) {
      // 이메일로 사용자 정보 가져오기
      api.get(`/api/users/email/${userEmail}`).then((response) => {
        const user = response.data;
        setNewReview((prevReview) => ({
          ...prevReview,
          name: user.name, // 서버에서 가져온 사용자 이름 설정
        }));
      });
    }
  }, [userEmail]);

  const fetchBookDetails = () => {
    api.get(`/api/books/${bookId}`).then((response) => {
      setBook(response.data);
    });
  };

  const fetchReviews = () => {
    api.get(`/api/reviews/book/${bookId}`).then((response) => {
      setReviews(response.data);
    });
  };

  useEffect(() => {
    fetchBookDetails();
    fetchReviews();

    api.post('/api/rentals/check', { bookId: Number(bookId) }).then((response) => {
      setIsRented(response.data);
    });

    if (userEmail) {
      api.get(`/api/reviews/user/${userEmail}`).then((response) => {
        const userReviews = response.data as ReviewDTO[];
        const hasReviewedBook = userReviews.some((review) => review.creationDate === newReview.creationDate);
        setHasReviewed(hasReviewedBook);
      });
    }
  }, [bookId, userEmail, newReview.creationDate]);

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

  const handleReviewChange = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLSelectElement | HTMLInputElement>) => {
    setNewReview({
      ...newReview,
      [e.target.name]: e.target.value,
    });
  };

  const handleReviewSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const reviewData = {
      ...newReview,
      bookId: book?.bookId // bookId 추가
    };
  
    api.post('/api/reviews', reviewData)
      .then(response => {
        setReviews([...reviews, response.data]);
        setHasReviewed(true);
        alert('리뷰가 성공적으로 작성되었습니다.');
      })
      .catch(error => console.error('리뷰 작성 중 오류 발생:', error));
  };

  return (
    <div>
      {book ? (
        <>
          <h1>{book.title}</h1>
          {book.imageUrl && <img src={book.imageUrl} alt={book.title} style={{ width: '300px', height: 'auto' }} />}
          <p>저자: {book.author}</p>
          <p>출판일: {new Date(book.publicationDate).toLocaleDateString()}</p>
          <p>재고 수량: {book.stockQuantity}</p>
          <p>카테고리: {book.category.name}</p>
          
          {!isRented ? (
            <button onClick={handleRentBook} disabled={!isAuthenticated}>
              대여하기
            </button>
          ) : (
            <button onClick={handleReturnBook}>반납하기</button>
          )}

          <h2>리뷰 목록</h2>
          <ul>
            {reviews.map(review => (
              <li key={review.reviewId}>
                <p>작성자: {review.name || '익명'} - 작성일: {review.creationDate ? new Date(review.creationDate).toLocaleDateString() : '알 수 없음'}</p>
                <p>평점: {review.rating}</p>
                <p>내용: {review.content}</p>
              </li>
            ))}
          </ul>

          {isRented && !hasReviewed && (
            <div>
              <h3>리뷰 작성</h3>
              <form onSubmit={handleReviewSubmit}>
                <div>
                  <label>평점:</label>
                  <input
                    type="number"
                    name="rating"
                    value={newReview.rating}
                    onChange={handleReviewChange}
                    min="1"
                    max="5"
                    required
                  />
                </div>
                <div>
                  <label>내용:</label>
                  <textarea
                    name="content"
                    value={newReview.content}
                    onChange={handleReviewChange}
                    required
                  />
                </div>
                <button type="submit">리뷰 작성</button>
              </form>
            </div>
          )}
        </>
      ) : (
        <p>도서 정보를 찾을 수 없습니다.</p>
      )}
    </div>
  );
};

export default BookDetailPage;
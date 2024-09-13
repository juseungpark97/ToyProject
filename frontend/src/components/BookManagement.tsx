// BookManagement.tsx
import React, { useEffect, useState } from 'react';
import api from '../utils/api';

interface Book {
  bookId: number;
  title: string;
  author: string;
  publicationDate: string;
  stockQuantity: number;
  imageUrl: string;
  categoryId: number | null;
}

interface Category {
  categoryId: number;
  name: string;
}

const BookManagement: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedBook, setSelectedBook] = useState<Book | null>(null);
  const [editingBook, setEditingBook] = useState<Book | null>(null);

  useEffect(() => {
    fetchBooks();
    fetchCategories();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await api.get('/api/books');
      setBooks(response.data);
    } catch (error) {
      console.error('Error fetching books:', error);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await api.get('/api/categories');
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const handleBookClick = (book: Book) => {
    setSelectedBook(book);

    // publicationDate를 yyyy-MM-dd 형식으로 변환
    const formattedBook = {
      ...book,
      publicationDate: book.publicationDate.split('T')[0], // 날짜 부분만 추출
    };

    setEditingBook(formattedBook);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement>) => {
    if (editingBook) {
      setEditingBook({
        ...editingBook,
        [e.target.name]: e.target.value,
      });
    }
  };

  const handleSaveChanges = async () => {
    if (!editingBook) return;

    try {
      const response = await api.put(`/api/books/${editingBook.bookId}`, editingBook);
      alert('도서 정보가 성공적으로 수정되었습니다.');
      setSelectedBook(null);
      fetchBooks(); // 변경된 도서 목록 다시 불러오기
    } catch (error) {
      console.error('Error updating book:', error);
    }
  };

  return (
    <div>
      <h2>도서 관리</h2>
      <ul>
        {books.map((book) => (
          <li key={book.bookId} onClick={() => handleBookClick(book)} style={{ cursor: 'pointer' }}>
            <img src={book.imageUrl} alt={book.title} style={{ width: '50px', height: '75px', marginRight: '10px' }} />
            {book.title} - {book.author}
          </li>
        ))}
      </ul>

      {selectedBook && editingBook && (
        <div>
          <h3>도서 정보 수정</h3>
          <form onSubmit={(e) => { e.preventDefault(); handleSaveChanges(); }}>
            <div>
              <label>제목:</label>
              <input
                type="text"
                name="title"
                value={editingBook.title}
                onChange={handleInputChange}
                required
              />
            </div>
            <div>
              <label>저자:</label>
              <input
                type="text"
                name="author"
                value={editingBook.author}
                onChange={handleInputChange}
                required
              />
            </div>
            <div>
              <label>출판일:</label>
              <input
                type="date"
                name="publicationDate"
                value={editingBook.publicationDate}
                onChange={handleInputChange}
                required
              />
            </div>
            <div>
              <label>재고 수량:</label>
              <input
                type="number"
                name="stockQuantity"
                value={editingBook.stockQuantity}
                onChange={handleInputChange}
                required
                min="0"
                step="1"
              />
            </div>
            <div>
              <label>카테고리:</label>
              <select
                name="categoryId"
                value={editingBook.categoryId ?? ''}
                onChange={handleInputChange}
                required
              >
                <option value="" disabled>카테고리를 선택하세요</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryId}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>
            <button type="submit">변경 사항 저장</button>
            <button onClick={() => setSelectedBook(null)}>취소</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default BookManagement;
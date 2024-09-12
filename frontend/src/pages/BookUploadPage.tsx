import React, { useState, useEffect } from 'react';
import api from '../utils/api';

interface Category {
  categoryId: number;
  name: string;
}

const BookUploadPage: React.FC = () => {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [publicationDate, setPublicationDate] = useState('');
  const [genre, setGenre] = useState('');
  const [stockQuantity, setStockQuantity] = useState(0);
  const [categoryId, setCategoryId] = useState<number | null>(null);
  const [file, setFile] = useState<File | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await api.get<Category[]>('/api/categories');
        setCategories(response.data);
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };
    fetchCategories();
  }, []);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      setFile(event.target.files[0]);
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    if (!file) return;

    const formData = new FormData();
    formData.append('file', file);
    formData.append('title', title);
    formData.append('author', author);
    formData.append('publicationDate', publicationDate);
    formData.append('genre', genre);
    formData.append('stockQuantity', stockQuantity.toString());
    formData.append('categoryId', categoryId?.toString() || '');

    try {
      const response = await api.post('/api/books/add', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert('책 업로드 성공 :  ' + response.data.title);
    } catch (error) {
      console.error('Error uploading book:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="title">Title:</label>
        <input
          type="text"
          id="title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="author">Author:</label>
        <input
          type="text"
          id="author"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="publicationDate">Publication Date:</label>
        <input
          type="date"
          id="publicationDate"
          value={publicationDate}
          onChange={(e) => setPublicationDate(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="genre">Genre:</label>
        <input
          type="text"
          id="genre"
          value={genre}
          onChange={(e) => setGenre(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="stockQuantity">Stock Quantity:</label>
        <input
          type="number"
          id="stockQuantity"
          value={stockQuantity}
          onChange={(e) => setStockQuantity(Number(e.target.value))}
          required
        />
      </div>
      <div>
        <label htmlFor="category">Category:</label>
        <select
          id="category"
          value={categoryId ?? ''}
          onChange={(e) => setCategoryId(Number(e.target.value))}
          required
        >
          <option value="" disabled>Select a category</option>
          {categories.map((category) => (
            <option key={category.categoryId} value={category.categoryId}>
              {category.name}
            </option>
          ))}
        </select>
      </div>
      <div>
        <label htmlFor="file">Image:</label>
        <input
          type="file"
          id="file"
          onChange={handleFileChange}
          required
        />
      </div>
      <button type="submit">Upload Book</button>
    </form>
  );
};

export default BookUploadPage;
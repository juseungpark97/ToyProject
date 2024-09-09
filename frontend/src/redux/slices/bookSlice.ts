// src/redux/slices/bookSlice.ts

import { createSlice, PayloadAction, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

interface BookState {
  books: any[];
  loading: boolean;
  error: string | null;
}

const initialState: BookState = {
  books: [],
  loading: false,
  error: null,
};

// 비동기 책 업로드 액션 생성
export const uploadBook = createAsyncThunk(
  'book/uploadBook',
  async (formData: FormData, { rejectWithValue }) => {
    try {
      const response = await axios.post('http://localhost:8080/api/books/add', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    } catch (error: any) {
      return rejectWithValue(error.response.data);
    }
  }
);

const bookSlice = createSlice({
  name: 'book',
  initialState,
  reducers: {
    // 필요한 동기 액션들 추가 가능
  },
  extraReducers: (builder) => {
    builder
      .addCase(uploadBook.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(uploadBook.fulfilled, (state, action: PayloadAction<any>) => {
        state.loading = false;
        state.books.push(action.payload);
      })
      .addCase(uploadBook.rejected, (state, action: PayloadAction<any>) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default bookSlice.reducer;
// src/redux/store.ts
import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice'; // 방금 만든 슬라이스 리듀서를 가져옵니다.

export const store = configureStore({
  reducer: {
    auth: authReducer, // 슬라이스 리듀서를 추가합니다.
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
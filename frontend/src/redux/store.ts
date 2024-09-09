import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // Local Storage 사용
import authReducer from './slices/authSlice'; // auth 슬라이스 리듀서
import bookReducer from './slices/bookSlice'; // book 슬라이스 리듀서

// redux-persist 설정
const persistConfig = {
  key: 'root',
  storage,
};

// 여러 리듀서를 결합
const rootReducer = combineReducers({
  auth: authReducer,
  book: bookReducer,
});

// persistReducer로 전체 root 리듀서를 감싸서 상태를 영구 저장하도록 설정
const persistedReducer = persistReducer(persistConfig, rootReducer);

// Redux 스토어 설정
export const store = configureStore({
  reducer: persistedReducer, // persistReducer를 사용하여 스토어 설정
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false, // 비직렬화 값 체크 비활성화
    }),
});

// Redux Persistor 생성 및 export
export const persistor = persistStore(store);

// RootState와 AppDispatch 타입 내보내기
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
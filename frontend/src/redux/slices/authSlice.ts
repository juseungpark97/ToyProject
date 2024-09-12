import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface AuthState {
  isAuthenticated: boolean;
  user: { email: string; role?: string } | null; // role 속성 추가
}

const initialState: AuthState = {
  isAuthenticated: false,
  user: null,
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<{ email: string; role?: string }>) => {
      state.isAuthenticated = true;
      state.user = action.payload; // 로그인 시 사용자 이메일과 역할을 상태에 저장
    },
    logout: (state) => {
      state.isAuthenticated = false;
      state.user = null; // 로그아웃 시 사용자 정보를 초기화
    },
  },
});

export const { login, logout } = authSlice.actions;
export default authSlice.reducer;
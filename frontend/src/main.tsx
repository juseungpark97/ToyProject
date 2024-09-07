import ReactDOM from 'react-dom/client'; // react-dom/client에서 createRoot를 가져옵니다.
import { Provider } from 'react-redux';
import { store } from './redux/store';
import App from './App';

const rootElement = document.getElementById('root'); // root 엘리먼트를 가져옵니다.
if (rootElement) {
  const root = ReactDOM.createRoot(rootElement); // createRoot로 새로운 루트를 생성합니다.
  root.render(
    <Provider store={store}>
      <App />
    </Provider>
  );
}
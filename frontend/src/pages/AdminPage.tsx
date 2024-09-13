// AdminPage.tsx
import React, { useState } from 'react';
import Sidebar from '../components/Sidebar'; // Sidebar 컴포넌트 가져오기
import UserManagement from '../components/UserManagement'; // UserManagement 컴포넌트 가져오기
import BookUpload from '../components/BookUpload'; // BookUpload 컴포넌트 가져오기
import BookManagement from '../components/BookManagement'; // BookManagement 컴포넌트 가져오기

const AdminPage: React.FC = () => {
  const [activeTab, setActiveTab] = useState('userManagement');

  const renderActiveTab = () => {
    switch (activeTab) {
      case 'userManagement':
        return <UserManagement />;
      case 'bookUpload':
        return <BookUpload />;
      case 'bookManagement':
        return <BookManagement />;
      default:
        return <UserManagement />;
    }
  };

  return (
    <div style={{ display: 'flex' }}>
      <Sidebar setActiveTab={setActiveTab} />
      <div style={{ flexGrow: 1, padding: '20px' }}>
        {renderActiveTab()}
      </div>
    </div>
  );
};

export default AdminPage;
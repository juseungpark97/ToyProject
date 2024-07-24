import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Modal from 'react-modal';

Modal.setAppElement('#root'); // 모달의 접근성을 위해 필수

function ServiceDetail() {
  const { id } = useParams();
  const [serviceDetail, setServiceDetail] = useState(null);
  const [modalIsOpen, setModalIsOpen] = useState(false);

  useEffect(() => {
    axios.get(`http://localhost:8088/api/fetchServiceDetail?serviceId=${id}`)
      .then(response => {
        setServiceDetail(response.data);
      });
  }, [id]);

  function openModal() {
    setModalIsOpen(true);
  }

  function closeModal() {
    setModalIsOpen(false);
  }

  return (
    <div>
      <button onClick={openModal}>Show Details</button>

      <Modal
        isOpen={modalIsOpen}
        onRequestClose={closeModal}
        contentLabel="Service Detail"
        className="modal"
        overlayClassName="overlay"
      >
        <button onClick={closeModal} className="close-button">×</button>
        {serviceDetail ? (
          <div>
            <h1>{serviceDetail.name}</h1>
            <p><strong>Purpose:</strong> {serviceDetail.purpose}</p>
            <p><strong>Criteria:</strong> {serviceDetail.criteria}</p>
            <p><strong>Support Details:</strong> {serviceDetail.support_details}</p>
            {/* 기타 필요한 정보들 */}
          </div>
        ) : (
          <p>Loading...</p>
        )}
      </Modal>
    </div>
  );
}

export default ServiceDetail;

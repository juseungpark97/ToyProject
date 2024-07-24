import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';

const ServiceListContainer = styled.div`
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 1200px;
  margin: auto;
`;

const Title = styled.h1`
  text-align: center;
  margin-bottom: 20px;
`;

const ServiceItem = styled.li`
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  background-color: #f9f9f9;
`;

const ServiceTitle = styled.h2`
  margin-top: 0;
`;

const ServiceDetails = styled.p`
  margin: 5px 0;
`;

const ServiceLink = styled.a`
  color: #1a73e8;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }
`;

const ServiceList = () => {
  const [services, setServices] = useState([]);

  useEffect(() => {
    const fetchServices = async () => {
      try {
        const response = await axios.get('http://localhost:8088/api/services');
        setServices(response.data);
      } catch (error) {
        console.error('Error fetching services:', error);
      }
    };

    fetchServices();
  }, []);

  return (
    <ServiceListContainer>
      <Title>공공 서비스 목록</Title>
      <ul>
        {services.map((service) => (
          <ServiceItem key={service.서비스ID}>
            <ServiceTitle>{service.서비스명}</ServiceTitle>
            <ServiceDetails><strong>부서명:</strong> {service.부서명}</ServiceDetails>
            <ServiceDetails><strong>사용자 구분:</strong> {service.사용자구분}</ServiceDetails>
            <ServiceDetails><strong>상세 조회 URL:</strong> <ServiceLink href={service.상세조회URL} target="_blank" rel="noopener noreferrer">링크</ServiceLink></ServiceDetails>
            <ServiceDetails><strong>서비스 목적 요약:</strong> {service.서비스목적요약}</ServiceDetails>
            <ServiceDetails><strong>서비스 분야:</strong> {service.서비스분야}</ServiceDetails>
            <ServiceDetails><strong>소관 기관명:</strong> {service.소관기관명}</ServiceDetails>
            <ServiceDetails><strong>소관 기관 유형:</strong> {service.소관기관유형}</ServiceDetails>
            <ServiceDetails><strong>소관 기관 코드:</strong> {service.소관기관코드}</ServiceDetails>
            <ServiceDetails><strong>신청 기한:</strong> {service.신청기한}</ServiceDetails>
            <ServiceDetails><strong>신청 방법:</strong> {service.신청방법}</ServiceDetails>
            <ServiceDetails><strong>전화 문의:</strong> {service.전화문의}</ServiceDetails>
            <ServiceDetails><strong>접수 기관:</strong> {service.접수기관}</ServiceDetails>
            <ServiceDetails><strong>조회수:</strong> {service.조회수}</ServiceDetails>
            <ServiceDetails><strong>지원 내용:</strong> {service.지원내용}</ServiceDetails>
            <ServiceDetails><strong>지원 대상:</strong> {service.지원대상}</ServiceDetails>
            <ServiceDetails><strong>지원 유형:</strong> {service.지원유형}</ServiceDetails>
          </ServiceItem>
        ))}
      </ul>
    </ServiceListContainer>
  );
};

export default ServiceList;

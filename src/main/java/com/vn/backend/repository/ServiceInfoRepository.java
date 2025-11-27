package com.vn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.backend.model.ServiceInfo;

public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, Long> {

}

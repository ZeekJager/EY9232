package com.trade.tradelicense.infrastructure.repositories;

import com.trade.tradelicense.domain.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.trade.tradelicense.infrastructure.persistence.JpaTradeLicenseApplicationEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataTradeLicenseApplicationRepository extends JpaRepository<JpaTradeLicenseApplicationEntity, UUID> {
    List<JpaTradeLicenseApplicationEntity> findByStatus(ApplicationStatus status);
}

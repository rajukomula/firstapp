package com.example.usermanagementpackage.repository;

import com.example.usermanagementpackage.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    void deleteByUserId(Long userId);
}
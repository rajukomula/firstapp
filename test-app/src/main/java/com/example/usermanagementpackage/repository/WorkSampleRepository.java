package com.example.usermanagementpackage.repository;


import com.example.usermanagementpackage.entity.WorkSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSampleRepository extends JpaRepository<WorkSample, Long> {
    void deleteByUserId(Long userId);
}

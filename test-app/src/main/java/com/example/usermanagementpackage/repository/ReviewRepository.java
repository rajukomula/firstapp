package com.example.usermanagementpackage.repository;

import com.example.usermanagementpackage.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId); // Fetch reviews received by a user
    void deleteByUserId(Long userId);

}
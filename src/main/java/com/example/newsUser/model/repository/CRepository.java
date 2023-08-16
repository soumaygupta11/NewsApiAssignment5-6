package com.example.newsUser.model.repository;

import com.example.newsUser.model.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CRepository extends JpaRepository<Call, Long> {
}


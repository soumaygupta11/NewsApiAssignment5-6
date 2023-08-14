package com.example.newsUser.model.repository;

import com.example.newsUser.model.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CRepository extends JpaRepository<Call, Long> {
}


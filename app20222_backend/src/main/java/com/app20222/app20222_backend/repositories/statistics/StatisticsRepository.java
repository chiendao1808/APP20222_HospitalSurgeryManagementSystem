package com.app20222.app20222_backend.repositories.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.surgery.Surgery;

@Repository
public interface StatisticsRepository extends JpaRepository<Surgery, Long> {

}

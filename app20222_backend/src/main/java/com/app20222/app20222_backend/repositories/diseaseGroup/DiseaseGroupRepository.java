package com.app20222.app20222_backend.repositories.diseaseGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.diseaseGroup.DiseaseGroup;

@Repository
public interface DiseaseGroupRepository extends JpaRepository<DiseaseGroup, Long> {

}

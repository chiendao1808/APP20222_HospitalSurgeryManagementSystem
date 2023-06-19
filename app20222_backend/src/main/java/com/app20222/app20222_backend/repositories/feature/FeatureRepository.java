package com.app20222.app20222_backend.repositories.feature;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.users.SQLUser;
import com.app20222.app20222_backend.entities.features.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    @Query(nativeQuery = true, value = SQLUser.GET_LIST_USER_FEATURES_BY_ROLES)
    Set<String> getLstUserFeaturesByRole(String roles);

}

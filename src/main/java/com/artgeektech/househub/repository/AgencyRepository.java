package com.artgeektech.househub.repository;

import com.artgeektech.househub.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by guang on 2:53 PM 5/3/18.
 */
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
}

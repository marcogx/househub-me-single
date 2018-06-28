package com.artgeektech.househub.repository;

import com.artgeektech.househub.domain.HouseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by guang on 5:35 PM 5/4/18.
 */
@Repository
public interface HouseUserRepository extends JpaRepository<HouseUser, Long> {
    HouseUser findByHouseIdAndUserIdAndType(Long houseId, Long userId, Integer type);

    HouseUser findByHouseIdAndType(Long houseId, Integer type);

    @Transactional
    void deleteByHouseIdAndUserIdAndType(Long houseId, Long userId, Integer type);
}

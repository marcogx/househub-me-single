package com.artgeektech.househub.repository;

import com.artgeektech.househub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by guang on 12:47 AM 4/21/18.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    Integer deleteByEmail(String email);

    User findByEmail(String email);

    @Query(
        value = "SELECT * FROM user u WHERE u.type = :type AND u.agency_id > 0 " +
                "LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    List<User> queryPageUser(@Param("type") Integer type, @Param("offset") Integer offset,
                             @Param("limit") Integer limit);

    @Query(value = "SELECT COUNT(*) FROM user u WHERE u.type = :type ",
        nativeQuery = true
    )
    Long queryPageCount(@Param("type") Integer type);
}

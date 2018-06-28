package com.artgeektech.househub.repository;

import com.artgeektech.househub.domain.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guang on 5:35 PM 5/4/18.
 */
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query(
        value = "SELECT * FROM house h WHERE h.type = :type AND " +
        "LOWER(h.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
        "ORDER BY h.updated_time DESC LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    List<House> queryPageHouseTimeDesc(@Param("term") String term, @Param("type") Integer type,
                                       @Param("offset") Integer offset, @Param("limit") Integer limit);


    @Query(value =
        "SELECT * FROM house h WHERE h.type = :type AND " +
        "LOWER(h.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
        "ORDER BY h.price DESC LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    List<House> queryPageHousePriceDesc(@Param("term") String term, @Param("type") Integer type,
                                       @Param("offset") Integer offset, @Param("limit") Integer limit);


    @Query(value = "SELECT * FROM house h WHERE h.type = :type AND " +
        "LOWER(h.name) LIKE LOWER(CONCAT('%', :term, '%')) " +
        "ORDER BY h.price LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    List<House> queryPageHousePriceAsc(@Param("term") String term, @Param("type") Integer type,
                                       @Param("offset") Integer offset, @Param("limit") Integer limit);


    @Query(value = "SELECT COUNT(*) FROM house h WHERE h.type = :type AND " +
        "LOWER(h.name) LIKE LOWER(CONCAT('%', :term, '%'))",
        nativeQuery = true
    )
    Long queryPageCount(@Param("term") String term, @Param("type") Integer type);


    @Query(value = "SELECT h.* FROM house AS h INNER JOIN house_user AS hu ON h.id = hu.house_id " +
        "WHERE hu.type = :houseUserType AND hu.user_id = :userId " +
        "LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    List<House> queryPageHouseUser(@Param("userId") Long userId, @Param("houseUserType") Integer houseUserType,
                                   @Param("offset") Integer offset, @Param("limit") Integer limit);


    @Query(value = "SELECT COUNT(*) FROM house AS h INNER JOIN house_user AS hu ON h.id = hu.house_id " +
        "WHERE hu.type = :houseUserType AND hu.user_id = :userId ",
        nativeQuery = true
    )
    Long queryPageCountHouseUser(@Param("userId") Long userId, @Param("houseUserType") Integer houseUserType);
}

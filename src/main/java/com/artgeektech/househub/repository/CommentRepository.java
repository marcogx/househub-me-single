package com.artgeektech.househub.repository;

import com.artgeektech.househub.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by guang on 2:34 AM 5/17/18.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
        value = "SELECT * FROM comments c WHERE c.house_id = :houseId ORDER BY c.create_time DESC LIMIT :limit",
        nativeQuery = true
    )
    List<Comment> getHouseComments(@Param("houseId") Long houseId, @Param("limit") Integer sizeLimit);

    @Query(
        value = "SELECT * FROM comments c WHERE c.agent_id = :agentId ORDER BY c.create_time DESC LIMIT :limit",
        nativeQuery = true
    )
    List<Comment> getAgentComments(@Param("agentId") Long agentId, @Param("limit") Integer sizeLimit);
}

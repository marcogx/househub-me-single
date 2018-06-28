package com.artgeektech.househub.repository;

import com.artgeektech.househub.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by guang on 4:03 PM 4/21/18.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findById(Long id);
    List<Note> findByContentIgnoreCaseContaining(String content);

    @Query(value = "SELECT * FROM notes n WHERE " +
        "LOWER(n.content) LIKE LOWER(CONCAT('%', :term, '%'))" +
        "ORDER BY n.updated_time DESC LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    List<Note> findBySearchTerm(@Param("term") String term, @Param("offset") Integer offset,
                                @Param("limit") Integer limit);

    @Query(value = "SELECT COUNT(*) FROM notes n WHERE " +
        "LOWER(n.content) LIKE LOWER(CONCAT('%', :term, '%'))",
        nativeQuery = true
    )
    Long findCount(@Param("term") String term);
}
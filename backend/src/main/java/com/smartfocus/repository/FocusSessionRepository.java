package com.smartfocus.repository;

import com.smartfocus.entity.FocusSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {

  @Query("""
      select fs
      from FocusSession fs
      join fs.task t
      where fs.id = :id and t.user.id = :userId
      """)
  Optional<FocusSession> findByIdForUser(@Param("id") Long id, @Param("userId") Long userId);

  @Query("""
      select fs
      from FocusSession fs
      join fs.task t
      where t.user.id = :userId
      order by fs.startedAt desc
      """)
  List<FocusSession> findAllForUser(@Param("userId") Long userId);

  @Query("""
      select coalesce(sum(fs.actualMinutes), 0)
      from FocusSession fs
      join fs.task t
      where t.user.id = :userId and fs.completed = true
      """)
  long sumCompletedMinutesForUser(@Param("userId") Long userId);
}


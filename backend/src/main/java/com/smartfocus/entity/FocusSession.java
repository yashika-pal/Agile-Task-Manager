package com.smartfocus.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "focus_sessions")
public class FocusSession {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id", nullable = false)
  private Task task;

  @Column(name = "planned_minutes", nullable = false)
  private Integer plannedMinutes = 25;

  @Column(name = "actual_minutes", nullable = false)
  private Integer actualMinutes = 0;

  @Column(nullable = false)
  private boolean completed = false;

  @Column(name = "started_at", nullable = false)
  private Instant startedAt = Instant.now();

  @Column(name = "completed_at")
  private Instant completedAt;

  public Long getId() {
    return id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public Integer getPlannedMinutes() {
    return plannedMinutes;
  }

  public void setPlannedMinutes(Integer plannedMinutes) {
    this.plannedMinutes = plannedMinutes;
  }

  public Integer getActualMinutes() {
    return actualMinutes;
  }

  public void setActualMinutes(Integer actualMinutes) {
    this.actualMinutes = actualMinutes;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public Instant getStartedAt() {
    return startedAt;
  }

  public Instant getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(Instant completedAt) {
    this.completedAt = completedAt;
  }
}


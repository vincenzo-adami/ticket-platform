package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface NoteRepository extends JpaRepository<Note, Long> {

  @Transactional
  void deleteByTicketId(Long id);
}
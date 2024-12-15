package org.lessons.ticketplatform.repository;

import java.util.List;

import org.lessons.ticketplatform.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

  List<Note> findByTicketId(Long id);
}
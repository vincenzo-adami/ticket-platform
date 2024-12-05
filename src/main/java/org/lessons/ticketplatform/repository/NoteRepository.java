package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
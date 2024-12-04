package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}

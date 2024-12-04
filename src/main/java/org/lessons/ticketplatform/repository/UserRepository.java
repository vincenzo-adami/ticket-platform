package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

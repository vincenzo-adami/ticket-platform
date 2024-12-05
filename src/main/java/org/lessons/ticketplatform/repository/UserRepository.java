package org.lessons.ticketplatform.repository;

import java.util.List;
import java.util.Optional;

import org.lessons.ticketplatform.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByRolesNameAndStatus(String roleName, boolean status);

  Optional<User> findByUsername(String username);

}
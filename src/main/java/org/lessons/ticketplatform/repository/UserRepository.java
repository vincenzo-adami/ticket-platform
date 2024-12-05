package org.lessons.ticketplatform.repository;

import java.util.Optional;

import org.lessons.ticketplatform.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByRoleNameAndStatus(String roleName, boolean status);

}
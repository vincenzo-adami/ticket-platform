package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}

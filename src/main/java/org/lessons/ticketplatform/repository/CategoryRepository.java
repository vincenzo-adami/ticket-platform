package org.lessons.ticketplatform.repository;

import org.lessons.ticketplatform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
package org.lessons.ticketplatform.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Category name cannot be blank")
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Ticket> tikcets;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Ticket> getTikcets() {
    return tikcets;
  }

  public void setTikcets(List<Ticket> tikcets) {
    this.tikcets = tikcets;
  }

}

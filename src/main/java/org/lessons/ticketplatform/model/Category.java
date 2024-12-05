package org.lessons.ticketplatform.model;

import java.util.List;

import jakarta.persistence.Column;
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

  @NotBlank
  @Column(unique = true, length = 30)
  private String name;

  @OneToMany(mappedBy = "category")
  private List<Ticket> ticket;

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

  public List<Ticket> getTicket() {
    return ticket;
  }

  public void setTicket(List<Ticket> ticket) {
    this.ticket = ticket;
  }
}
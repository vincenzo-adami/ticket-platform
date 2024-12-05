package org.lessons.ticketplatform.security;

import java.util.Optional;

import org.lessons.ticketplatform.model.User;
import org.lessons.ticketplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> userByUsername = userRepo.findByUsername(username);

    if (userByUsername.isPresent()) {

      return new DatabaseUserDetails(userByUsername.get());

    } else {

      throw new UsernameNotFoundException("Username not found");

    }
  }

}

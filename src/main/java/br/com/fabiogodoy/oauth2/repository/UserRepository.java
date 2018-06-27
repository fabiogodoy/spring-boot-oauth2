package br.com.fabiogodoy.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fabiogodoy.oauth2.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
}

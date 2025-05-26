package com.casacampo.proj.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casacampo.proj.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByCorreo(String correo);

}

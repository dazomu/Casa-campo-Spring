package com.casacampo.proj.services;



import java.util.List;
import java.util.Optional;

import com.casacampo.proj.entities.User;

public interface UserService {

    User save(User user);

    List<User> findAll();

    Boolean update(User user);

    Boolean delete(Long id);

    Optional<User> findByCorreo(String correo);
}

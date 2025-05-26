package com.casacampo.proj.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casacampo.proj.entities.User;
import com.casacampo.proj.repositories.UserRepository;
import com.casacampo.proj.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean update(User user) {

       if (userRepository.existsById(user.getId_user())) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
         if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findByCorreo(String correo) {
    Optional<User> userOptional = userRepository.findByCorreo(correo);

    return userOptional;
    
    }

}

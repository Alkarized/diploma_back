package org.example.diploma_jwt.services.model;

import jakarta.persistence.EntityExistsException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.*;
import org.example.diploma_jwt.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setOzon(new Ozon(user));
        user.setVk(new VK(user));
        user.setAvito(new Avito(user));
        user.setSettings(new Settings(user));

        return repository.save(user);
    }

    public User create(User user) throws EntityExistsException {
        if (repository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException("Пользователь с таким именем уже существует"); //todo
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new EntityExistsException("Пользователь с таким email уже существует"); //todo
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    public Optional<User> getByLogin(@NonNull String login) {
        return repository.findByUsername(login);
    }
}

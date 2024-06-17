package com.example.timeder.user;

import com.example.timeder.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getIndex(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getStatus());
        this.userRepository.save(newUser);
        return newUser;
    }

    public List<UserDTO> getUsers() {
        List<UserDTO> users = new ArrayList<>();
        List<User> usersFromDb = userRepository.findAll();

        for(User user : usersFromDb) {
            users.add(mapToDTO(user));
        }

        return users;
    }

    public UserDTO getUser(int id) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        return mapToDTO(userOptional.get());
    }

    @Transactional
    public User updateUser(int id, UserDTO userDTO) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);

        if (userDTO.getFirstName() != null) {
            updatedUser.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            updatedUser.setLastName(userDTO.getLastName());
        }
        if (userDTO.getIndex() != null) {
            updatedUser.setIndex(userDTO.getIndex());
        }
        if (userDTO.getEmail() != null) {
            updatedUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null) {
            updatedUser.setPassword(userDTO.getPassword());
        }
        if (userDTO.getStatus() != null) {
            updatedUser.setStatus(userDTO.getStatus());
        }

        return this.userRepository.save(updatedUser);
    }

    public void deleteUser(int id) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        this.userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getIndex(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus()
        );
    }
}

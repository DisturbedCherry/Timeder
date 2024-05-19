package com.example.timeder.user;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getIndex(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getStatus());
        this.userRepository.save(newUser);
        return newUser;
    }

    // READ

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // UPDATE

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

    // REMOVE

    public void deleteUser(int id) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        this.userRepository.deleteById(id);
    }
}

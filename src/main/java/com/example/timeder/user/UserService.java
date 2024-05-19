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

    public void updateUserFirstName(int id, String firstName) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);
        updatedUser.setFirstName(firstName);
        this.userRepository.save(updatedUser);
    }

    public void updateUserLastName(int id, String lastName) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);
        updatedUser.setLastName(lastName);
        this.userRepository.save(updatedUser);
    }

    public void updateUserIndex(int id, Integer index) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);
        updatedUser.setIndex(index);
        this.userRepository.save(updatedUser);
    }

    public void updateUserEmail(int id, String email) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);
        updatedUser.setPassword(email);
        this.userRepository.save(updatedUser);
    }

    public void updateUserPassword(int id, String password) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);
        updatedUser.setPassword(password);
        this.userRepository.save(updatedUser);
    }

    public void updateUserStatus(int id, UserStatus userStatus) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        User updatedUser = this.userRepository.getReferenceById(id);
        updatedUser.setStatus(userStatus);
        this.userRepository.save(updatedUser);
    }

    // REMOVE

    public void deleteUser(int id) throws ResourceNotFoundException {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }

        this.userRepository.deleteById(id);
    }
}

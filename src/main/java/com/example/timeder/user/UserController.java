package com.example.timeder.user;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE

    @PostMapping("/")
    public User createUser(@RequestBody UserDTO userDTO) {
        return this.userService.createUser(userDTO);
    }

    // READ

    @GetMapping("/")
    public List<UserDTO> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id) {
        try {
            return this.userService.getUser(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // UPDATE

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        try {
            return this.userService.updateUser(id, userDTO);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        try {
            this.userService.deleteUser(id);
        } catch (Exception ignore) {
        }
    }

}

package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@CrossOrigin(origins = "http://localhost:3000")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO createdGroup = this.groupService.createGroup(groupDTO);
            return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<GroupDTO>> getGroups() {
        List<GroupDTO> groups = this.groupService.getGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable int id) {
        try {
            GroupDTO group = this.groupService.getGroup(id);
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable int userId) {
        try {
            List<GroupDTO> groups = this.groupService.getUserGroups(userId);
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserGroupDTO>> getGroupMembers(@PathVariable int id) {
        try {
            List<UserGroupDTO> members = this.groupService.getGroupMembers(id);
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserGroupDTO> addUserToGroup(@RequestBody CreateUserGroupDTO createUserGroupDTO) {
        try {
            UserGroupDTO addedUser = this.groupService.addUserToGroup(createUserGroupDTO);
            return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable int id, @RequestBody GroupDTO groupDTO) {
        try {
            GroupDTO updatedGroup = this.groupService.updateGroup(id, groupDTO);
            return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable int id) {
        try {
            this.groupService.deleteGroup(id);
            return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteGroupMember(@RequestBody DeleteUserGroupDTO deleteUserGroupDTO) {
        try {
            this.groupService.deleteMember(deleteUserGroupDTO);
            return new ResponseEntity<>("Member deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Member not found", HttpStatus.NOT_FOUND);
        }
    }

}

package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import com.example.timeder.user.UserDTO;
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

    // CREATE

    @PostMapping("/")
    public GroupDTO createGroup(@RequestBody GroupDTO groupDTO) throws ResourceNotFoundException {
        try {
            return this.groupService.createGroup(groupDTO);
        }
        catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // READ

    @GetMapping("/")
    public List<GroupDTO> getGroups() {
        return this.groupService.getGroups();
    }

    @GetMapping("/{id}")
    public GroupDTO getGroup(@PathVariable int id) {
        try {
            return this.groupService.getGroup(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @GetMapping("/{id}/users")
    public List<UserGroupDTO> getGroupMembers(@PathVariable int id) {
        try {
            return this.groupService.getGroupMembers(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @PostMapping("/users")
    public UserGroupDTO addUserToGroup(@RequestBody CreateUserGroupDTO createUserGroupDTO) {
        try {
            return this.groupService.addUserToGroup(createUserGroupDTO);
        }
        catch (ResourceNotFoundException e) {
            return  null;
        }
    }

    // UPDATE

    @PutMapping("/{id}")
    public GroupDTO updateGroup(@PathVariable int id, @RequestBody GroupDTO groupDTO) {
        try {
            return this.groupService.updateGroup(id, groupDTO);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable int id) {
        try {
            this.groupService.deleteGroup(id);
        } catch (Exception ignore) {
        }
    }

}

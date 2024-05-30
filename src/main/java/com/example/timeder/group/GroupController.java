package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // CREATE

    @PostMapping("/")
    public Group createGroup(@RequestBody GroupDTO groupDTO) {
        return this.groupService.createGroup(groupDTO);
    }

    // READ

    @GetMapping("/")
    public List<Group> getGroups() {
        return this.groupService.getGroups();
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable int id) {
        try {
            return this.groupService.getGroup(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @GetMapping("/{id}/users")
    public List<User> getGroupMembers(@PathVariable int id) {
        try {
            return this.groupService.getGroupMembers(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // UPDATE

    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable int id, @RequestBody GroupDTO groupDTO) {
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

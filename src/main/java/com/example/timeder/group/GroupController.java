package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

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

    @PostMapping("/")
    public Group createGroup(@RequestBody GroupDTO groupDTO) {
        return this.groupService.createGroup(groupDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable int id) {
        try {
            this.groupService.deleteGroup(id);
        } catch (Exception ignore) {
        }
    }
}

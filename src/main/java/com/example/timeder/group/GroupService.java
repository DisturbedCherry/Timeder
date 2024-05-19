package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // CREATE

    public Group createGroup(GroupDTO groupDTO) {
        Group newGroup = new Group(groupDTO.getDescription(), groupDTO.getTotalSize(), groupDTO.getTotalSize(), groupDTO.getIsPrivate(), groupDTO.getJoinCode(), groupDTO.getOwner());
        this.groupRepository.save(newGroup);
        return newGroup;
    }

    // READ

    public List<Group> getGroups() {
        return this.groupRepository.findAll();
    }

    public Group getGroup(int id) throws ResourceNotFoundException {
        return this.groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }

    // UPDATE

    public void updateGroup(int id, GroupDTO groupDTO) throws ResourceNotFoundException {
        if (!this.groupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Group not found");
        }

        Group updatedGroup = this.groupRepository.getReferenceById(id);
        
        if (groupDTO.getDescription() != null) {
            updatedGroup.setDescription(groupDTO.getDescription());
        }
        if (groupDTO.getCurrentSize() != null) {
            updatedGroup.setCurrentSize(groupDTO.getCurrentSize());
        }
        if (groupDTO.getTotalSize() != null) {
            updatedGroup.setTotalSize(groupDTO.getTotalSize());
        }
        if (groupDTO.getIsPrivate() != null) {
            updatedGroup.setIsPrivate(groupDTO.getIsPrivate());
        }
        if (groupDTO.getJoinCode() != null) {
            updatedGroup.setJoinCode(groupDTO.getJoinCode());
        }
        if (groupDTO.getOwner() != null) {
            updatedGroup.setOwner(groupDTO.getOwner());
        }

        this.groupRepository.save(updatedGroup);
    }

    // TODO Dodanie uytkownika do grupy na podstawie np, indeksu, imienia i nazwiska

    // REMOVE

    public void deleteGroup(int id) throws ResourceNotFoundException {
        if (!this.groupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Group not found");
        }

        this.groupRepository.deleteById(id);
    }
}

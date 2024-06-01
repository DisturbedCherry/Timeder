package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import com.example.timeder.user.UserDTO;
import com.example.timeder.user.UserRepository;
import com.example.timeder.usergroup.UserGroup;
import com.example.timeder.usergroup.UserGroupRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }

    // CREATE

    public GroupDTO createGroup(GroupDTO groupDTO) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(Math.toIntExact(groupDTO.getOwnerId()));

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        Group newGroup = new Group(groupDTO.getName(), groupDTO.getDescription(), groupDTO.getTotalSize(), groupDTO.getTotalSize(), groupDTO.getIsPrivate(), groupDTO.getJoinCode(), user.get());
        this.groupRepository.save(newGroup);
        return mapToDTO(newGroup);
    }

    // READ

    public List<GroupDTO> getGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = new ArrayList<>();

        for(Group group : groups) {
            groupDTOs.add(mapToDTO(group));
        }

        return groupDTOs;
    }

    public GroupDTO getGroup(int id) throws ResourceNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(id);

        if(groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        return mapToDTO(groupOptional.get());
    }

    public List<UserGroupDTO> getGroupMembers(int id) throws ResourceNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(id);

        if(groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        List<User> users = groupOptional.get().getUserGroups().stream()
                .map(UserGroup::getUser)
                .toList();

        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();

        for(User user : users) {
            userGroupDTOs.add(mapToUserGroupDTO(user));
        }

        return userGroupDTOs;
    }

    // UPDATE

    public GroupDTO updateGroup(int id, GroupDTO groupDTO) throws ResourceNotFoundException {
        if (!this.groupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Group not found");
        }

        Group updatedGroup = groupRepository.getReferenceById(id);

        if (groupDTO.getName() != null) {
            updatedGroup.setName(groupDTO.getName());
        }
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

        return mapToDTO(groupRepository.save(updatedGroup));
    }

    public UserGroupDTO addUserToGroup(CreateUserGroupDTO createUserGroupDTO) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findByIndex(createUserGroupDTO.getIndex());
        Optional<Group> groupOptional = groupRepository.findById(createUserGroupDTO.getGroupId());

        if(userOptional.isEmpty() || groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        UserGroup userGroup = new UserGroup(userOptional.get(), groupOptional.get());
        userGroupRepository.save(userGroup);

        return mapToUserGroupDTO(userOptional.get());
    }
    // DELETE

    public void deleteGroup(int id) throws ResourceNotFoundException {
        if (!this.groupRepository.existsById(id)) {
            throw new ResourceNotFoundException("Group not found");
        }

        groupRepository.deleteById(id);
    }

    private GroupDTO mapToDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName(group.getName());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setCurrentSize(group.getCurrentSize());
        groupDTO.setTotalSize(group.getTotalSize());
        groupDTO.setIsPrivate(group.getIsPrivate());
        groupDTO.setJoinCode(group.getJoinCode());
        groupDTO.setOwnerId(group.getOwner().getId());
        return groupDTO;
    }

    private UserGroupDTO mapToUserGroupDTO(User user) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setFirstName(user.getFirstName());
        userGroupDTO.setLastName(user.getLastName());
        userGroupDTO.setIndex(user.getIndex());
        return userGroupDTO;
    }
}

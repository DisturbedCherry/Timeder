package com.example.timeder.group;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.groupevent.GroupEvent;
import com.example.timeder.groupevent.GroupEventRepository;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import com.example.timeder.usergroup.UserGroup;
import com.example.timeder.usergroup.UserGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupEventRepository groupEventRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, UserGroupRepository userGroupRepository, GroupEventRepository groupEventRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.groupEventRepository = groupEventRepository;
    }

    @Transactional
    public GroupDTO createGroup(GroupDTO groupDTO) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(Math.toIntExact(groupDTO.getOwnerId()));

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        if(groupRepository.existsByJoinCode(groupDTO.getJoinCode())) {
            throw new IllegalArgumentException("Join code already exists");
        }

        Group newGroup = new Group(groupDTO.getName(), groupDTO.getDescription(),1, groupDTO.getTotalSize(), groupDTO.getIsPrivate(), groupDTO.getJoinCode(), user.get());

        this.groupRepository.save(newGroup);

        UserGroup userGroup = new UserGroup(user.get(), newGroup);
        userGroupRepository.save(userGroup);

        return mapToDTO(newGroup);
    }

    public List<GroupDTO> getGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOs = new ArrayList<>();

        for (Group group : groups) {
            groupDTOs.add(mapToDTO(group));
        }

        return groupDTOs;
    }

    public GroupDTO getGroup(int id) throws ResourceNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(id);

        if (groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        return mapToDTO(groupOptional.get());
    }

    @Transactional
    public List<UserGroupDTO> getGroupMembers(int id) throws ResourceNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(id);

        if (groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        List<User> users = groupOptional.get().getUserGroups().stream()
                .map(UserGroup::getUser)
                .toList();

        List<UserGroupDTO> userGroupDTOs = new ArrayList<>();

        for (User user : users) {
            userGroupDTOs.add(mapToUserGroupDTO(user));
        }

        return userGroupDTOs;
    }

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

    @Transactional
    public UserGroupDTO addUserToGroup(CreateUserGroupDTO createUserGroupDTO) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findByIndex(createUserGroupDTO.getIndex());
        Optional<Group> groupOptional = groupRepository.findById(createUserGroupDTO.getGroupId());

        if (userOptional.isEmpty() || groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("User or Group not found");
        }

        if(Objects.equals(groupOptional.get().getCurrentSize(), groupOptional.get().getTotalSize())) {
            throw new IllegalArgumentException("Group is already full");
        }

        UserGroup userGroup = new UserGroup(userOptional.get(), groupOptional.get());

        Optional<UserGroup> existingUserGroupOptional = userGroupRepository.findByUserAndGroup(userOptional.get(), groupOptional.get());
        if (existingUserGroupOptional.isPresent()) {
            throw new IllegalArgumentException("User is already a member of the group");
        }

        userGroupRepository.save(userGroup);

        groupOptional.get().getUserGroups().add(userGroup);
        userOptional.get().getUserGroups().add(userGroup);

        groupOptional.get().setCurrentSize(groupOptional.get().getCurrentSize() + 1);
        groupRepository.save(groupOptional.get());

        return mapToUserGroupDTO(userOptional.get());
    }

    public void deleteGroup(int id) throws ResourceNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(id);

        if (groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        for (UserGroup userGroup : groupOptional.get().getUserGroups()) {
            if (Objects.equals(userGroup.getGroup().getId(), id)) {
                userGroupRepository.delete(userGroup);
            }
        }

        for (GroupEvent groupEvent : groupOptional.get().getGroupEvents()) {
            if (Objects.equals(groupEvent.getGroup().getId(), id)) {
                groupEventRepository.delete(groupEvent);
            }
        }

        groupRepository.deleteById(id);
    }

    public void deleteMember(DeleteUserGroupDTO deleteUserGroupDTO) throws ResourceNotFoundException {
        Optional<Group> groupOptional = groupRepository.findById(deleteUserGroupDTO.getGroupId());

        if (groupOptional.isEmpty()) {
            throw new ResourceNotFoundException("Group not found");
        }

        Optional<User> userOptional = userRepository.findByIndex(Math.toIntExact(deleteUserGroupDTO.getUserIndex()));
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        // Check if the user to be removed is the owner of the group
        if (Objects.equals(groupOptional.get().getOwner().getId(), userOptional.get().getId())) {
            throw new IllegalArgumentException("Cannot remove the owner of the group");
        }

        List<UserGroup> userGroups = groupOptional.get().getUserGroups();

        for (UserGroup userGroup : userGroups) {
            if (Objects.equals(userGroup.getUser().getId(), userOptional.get().getId())) {
                userGroupRepository.delete(userGroup);
            }
        }

        groupOptional.get().setCurrentSize(groupOptional.get().getCurrentSize() - 1);
        groupRepository.save(groupOptional.get());
    }

    private GroupDTO mapToDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
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

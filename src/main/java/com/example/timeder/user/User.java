package com.example.timeder.user;

import com.example.timeder.userevent.UserEvent;
import com.example.timeder.usergroup.UserGroup;
import com.example.timeder.usernotification.UserNotification;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private Integer index;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // mappedBy -> indicates that user is not owning side of relationship
    // cascade -> any operation performed on User will be cascaded to associated Notification entities
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserNotification> userNotifications = Collections.emptyList();

    public User(String firstName, String lastName, Integer index, String email, String password, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.index = index;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.status.name()));
    }

    // One-to-many relationship from User to UserGroup
    // cascade -> will delete all associated UserGroup rows when User is deleted
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserGroup> userGroups;

    // One-to-many relationship from User to UserEvent
    // cascade -> will delete all associated UserEvent rows when User is deleted
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserEvent> userEvents;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.index);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
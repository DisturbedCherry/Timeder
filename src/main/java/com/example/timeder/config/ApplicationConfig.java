package com.example.timeder.config;

import com.example.timeder.errorreport.*;
import com.example.timeder.group.GroupRepository;
import com.example.timeder.notification.NotificationRepository;
import com.example.timeder.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> this.userRepository.findByIndex(Integer.valueOf(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDatabase(UserService userService, ErrorReportService errorReportService) {
        return args -> {
            if(userRepository.count() == 0) {
                UserDTO user1 = new UserDTO("Robert", "Kubica", 123456, "robert@gmail.com", "123456", UserStatus.ACTIVE);
                UserDTO user2 = new UserDTO("Jakub", "Kowalski", 245822, "jakub.kowalski@example.com", "245822", UserStatus.ACTIVE);
                UserDTO user3 = new UserDTO("Robert", "Lewandowski", 1, "robert.lewy@example.com", "password", UserStatus.ACTIVE);

                userService.createUser(user1);
                userService.createUser(user2);
                userService.createUser(user3);
            }
        };
    }

}

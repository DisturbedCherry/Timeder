package com.example.timeder.notification;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import com.example.timeder.usernotification.UserNotification;
import com.example.timeder.usernotification.UserNotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, UserNotificationRepository userNotificationRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.userNotificationRepository = userNotificationRepository;
    }

    public void sendNotificationToAllUsers(NotificationDTO notificationDTO) {
        List<User> users = userRepository.findAll();
        Notification newNotification = new Notification(notificationDTO.getContent(), LocalDate.now());
        notificationRepository.save(newNotification);
        for(User user : users) {
            UserNotification userNotification = new UserNotification(user, newNotification);
            userNotificationRepository.save(userNotification);
        }
    }

    public List<NotificationDTO> getNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for(Notification notification : notifications) {
            notificationDTOS.add(mapToNotificationDTO(notification));
        }

        return notificationDTOS;
    }

    public NotificationDTO getNotification(int id) throws ResourceNotFoundException {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);

        if(notificationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Notification not found");
        }

        return mapToNotificationDTO(notificationOptional.get());
    }

    public NotificationDTO updateNotification(int id, NotificationDTO notificationDTO) throws ResourceNotFoundException {
        Notification updatedNotification = notificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        if (notificationDTO.getContent() != null) {
            updatedNotification.setContent(notificationDTO.getContent());
        }
        if (notificationDTO.getDateTime() != null) {
            updatedNotification.setDateTime(notificationDTO.getDateTime());
        }

        return mapToNotificationDTO(notificationRepository.save(updatedNotification));
    }

    public void deleteNotification(int id) throws ResourceNotFoundException {
        if (!this.notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found");
        }

        this.notificationRepository.deleteById(id);
    }

    public NotificationDTO mapToNotificationDTO(Notification notification) {
        return new NotificationDTO(notification.getContent(), notification.getDateTime());
    }
}
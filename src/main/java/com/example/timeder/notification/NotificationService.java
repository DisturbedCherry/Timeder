package com.example.timeder.notification;

import com.example.timeder.exception.ResourceNotFoundException;
import com.example.timeder.user.User;
import com.example.timeder.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // CREATE

    public void sendNotificationToAllUsers(NotificationDTO notificationDTO) {
        List<User> users = userRepository.findAll();

        for(User user : users) {
            Notification newNotification = new Notification(notificationDTO.getContent(), LocalDate.now(), user);
            notificationRepository.save(newNotification);
        }
    }

    // READ

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotification(int id) throws ResourceNotFoundException {
        return notificationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }

    // UPDATE

    public Notification updateNotification(int id, NotificationDTO notificationDTO) throws ResourceNotFoundException {
        if (!this.notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found");
        }

        Notification updatedNotification = this.notificationRepository.getReferenceById(id);

        if (notificationDTO.getContent() != null) {
            updatedNotification.setContent(notificationDTO.getContent());
        }
        if (notificationDTO.getDateTime() != null) {
            updatedNotification.setDateTime(notificationDTO.getDateTime());
        }

        return this.notificationRepository.save(updatedNotification);
    }

    // DELETE

    public void deleteNotification(int id) throws ResourceNotFoundException {
        if (!this.notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found");
        }

        Notification notification = this.notificationRepository.findById(id).get();
        User user = notification.getUser();
        user.getNotifications().remove(notification);

        this.notificationRepository.deleteById(id);
    }

}

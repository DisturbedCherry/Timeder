package com.example.timeder.notification;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // CREATE

    public Notification createNotification(NotificationDTO notificationDTO) {
        Notification newNotification = new Notification(notificationDTO.getContent(), LocalDate.now());
        notificationRepository.save(newNotification);
        return newNotification;
    }

    // READ

    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotification(int id) throws ResourceNotFoundException {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }

    // UPDATE NOT NEEDED

    // DELETE

    public void deleteNotification(int id) throws ResourceNotFoundException {
        if (!this.notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found");
        }

        this.notificationRepository.deleteById(id);
    }
}

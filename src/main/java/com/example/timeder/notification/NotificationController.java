package com.example.timeder.notification;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // CREATE

    @PostMapping("/")
    public Notification createNotification(@RequestBody NotificationDTO notificationDTO) {
        return this.notificationService.createNotification(notificationDTO);
    }

    // READ

    @GetMapping("/")
    public List<Notification> getNotifications() {
        return this.notificationService.getNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable int id) {
        try {
            return this.notificationService.getNotification(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // UPDATE

    @PutMapping("/{id}")
    public Notification updateNotification(@PathVariable int id, @RequestBody NotificationDTO notificationDTO) {
        try {
            return this.notificationService.updateNotification(id, notificationDTO);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable int id) {
        try {
            this.notificationService.deleteNotification(id);
        } catch (Exception ignore) {
        }
    }

}

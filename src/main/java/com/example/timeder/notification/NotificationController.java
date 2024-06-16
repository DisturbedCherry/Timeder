package com.example.timeder.notification;

import com.example.timeder.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // CREATE

    @PostMapping("/sendToAll")
    public ResponseEntity<String> sendNotificationToAllUsers(@RequestBody NotificationDTO notificationDTO) {
        notificationService.sendNotificationToAllUsers(notificationDTO);
        return ResponseEntity.ok("Notifications sent to all users.");
    }

    // READ

    @GetMapping("/")
    public ResponseEntity<List<NotificationDTO>> getNotifications() {
        List<NotificationDTO> notifications = this.notificationService.getNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable int id) {
        try {
            NotificationDTO notification = this.notificationService.getNotification(id);
            return ResponseEntity.ok(notification);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable int id, @RequestBody NotificationDTO notificationDTO) {
        try {
            NotificationDTO updatedNotification = this.notificationService.updateNotification(id, notificationDTO);
            return ResponseEntity.ok(updatedNotification);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        try {
            this.notificationService.deleteNotification(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.marketService.notificationservice.web.controller;

import com.marketService.notificationservice.business.service.OrderNotificationListenerService;
import com.marketService.notificationservice.model.Notifications;
import com.marketService.notificationservice.openAPI.DescriptionVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = DescriptionVariables.NOTIFICATIONS, description = "Used to get and delete Notifications")
@RequestMapping("api/v1/notifications")
@Slf4j
@AllArgsConstructor
public class OrderNotificationListenerController {

    @Autowired
    private OrderNotificationListenerService service;

    @GetMapping("/allNotifications")
    @Operation(
            summary = "Get All Notifications",
            description = "Retrieve all notifications from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request has succeeded"),
                    @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseEntity<List<Notifications>> getAllNotificationEntries() {
        List<Notifications> list = service.getAllNotifications();
        if (list.isEmpty()) {
            log.info("Empty Notification list found");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        log.info("List size: {}", list.size());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getByID/{id}")
    @Operation(
            summary = "Find Notification by id",
            description = "Retrieve notification from the database by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request has succeeded"),
                    @ApiResponse(responseCode = "404", description = "The server has not found anything matching the Request-URI"),
                    @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseEntity<Notifications> getNotificationById(@Parameter(description = "ID of the Notification entry", required = true)
                                                             @PathVariable("id") String id) {
        Optional<Notifications> notificationOptional = service.findNotificationById(id);
        if (notificationOptional.isPresent()) {
            Notifications notification = notificationOptional.get();
            log.info("Found Notification with id {} ", id);
            return ResponseEntity.status(HttpStatus.OK).body(notification);
        }
        log.warn("Notification not found with id: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                "Message", "Notification not found with id: " + id).build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Deletes Notification by id",
            description = "Provide an id to delete specific Notification entry from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The request has succeeded"),
                    @ApiResponse(responseCode = "404", description = "The server has not found anything matching the Request-URI"),
                    @ApiResponse(responseCode = "500", description = "Server error")})
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        if (service.findNotificationById(id).isPresent()) {
            service.deleteNotificationById(id);
            log.info("Notification entry with ID: {} deleted", id);
            return ResponseEntity.ok("Notification entry with ID " + id + " deleted");
        }
        log.warn("Cannot delete Notification entry with ID: {}, entry not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification entry not found with ID: " + id);
    }
}

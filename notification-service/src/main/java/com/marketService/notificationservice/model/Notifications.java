package com.marketService.notificationservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Notifications {
    @Schema(description = "The ID of the entity")
    private String id;

    @Schema(description = "The message of placed order in orderService received from kafka")
    private String message;
}

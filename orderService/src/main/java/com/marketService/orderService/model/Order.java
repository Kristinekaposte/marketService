package com.marketService.orderService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(description = "Model of Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Order {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The unique ID of the order")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The unique order number")
    private String orderNumber;

    @NotNull(message = "Customer ID cannot be null")
    @ApiModelProperty(notes = "The customer ID", example = "1")
    private Long customerId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The order time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The total price of the order")
    private Double totalPrice;

    @ApiModelProperty(notes = "List of order items")
    private List<OrderItem> orderItems;
}

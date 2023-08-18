package com.marketService.orderService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel(description = "Model of Order Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderItem {
    @ApiModelProperty(notes = "The unique ID of the order")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The id of the order")
    private Long orderId;

    @NotNull(message = "Product ID cannot be null")
    @ApiModelProperty(notes = "The ID of the product", example = "1")
    private Long productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "The price of the item")
    private Double itemPrice;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be a positive number")
    @ApiModelProperty(notes = "The quantity of the item", example = "3")
    private Integer quantity;
}

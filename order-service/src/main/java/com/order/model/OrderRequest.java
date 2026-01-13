package com.order.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderRequest{

    @NotBlank(message = "Customer email is required")
    @Email(message = "Email should be valid")
    private String customerEmail;

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}

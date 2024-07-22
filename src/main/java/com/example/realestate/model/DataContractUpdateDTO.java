package com.example.realestate.model;

import java.math.BigDecimal;

public record DataContractUpdateDTO(
        BigDecimal price,

        String description
) {
}

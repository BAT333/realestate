package com.example.realestate.model;

import com.example.realestate.domain.ContractType;
import com.example.realestate.domain.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DataUpdatePropertieDTO(

        @Min(1)
        Integer quantityRoom,
        @DecimalMin(value = "0.0",inclusive = false)
        @Digits(integer=3, fraction=12)

        BigDecimal price,
        String description,

        ContractType type,

        PropertyType propertyType,
        @Valid
        DataUpdateAddressDTO address
) {
}

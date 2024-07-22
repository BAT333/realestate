package com.example.realestate.model;

import com.example.realestate.domain.ContractType;
import com.example.realestate.domain.Propertie;
import com.example.realestate.domain.PropertyType;

import java.math.BigDecimal;

public record DataPropertieSearch(
        Long id,
        int quantityRoom,
        BigDecimal price,
        String description,
        ContractType type,
        PropertyType propertyType,
        DataAddressDTO address
) {
    public DataPropertieSearch(Propertie propertie) {
        this(propertie.getId(), propertie.getQuantityRoom(), propertie.getPrice(),
                propertie.getDescription(), propertie.getType(),propertie.getPropertyType(),new DataAddressDTO(propertie.getAddress()));
    }
}

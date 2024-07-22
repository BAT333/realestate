package com.example.realestate.model;

import com.example.realestate.domain.Address;
import com.example.realestate.domain.ContractType;
import com.example.realestate.domain.Propertie;
import com.example.realestate.domain.PropertyType;

import java.math.BigDecimal;

public record DataPropertie(
        Long id,
        int quantityRoom,
        BigDecimal price,
        String description,
        ContractType type,
        PropertyType propertyType,
        DataProprietor proprietor,
        DataAddressDTO address
) {
    public DataPropertie(Propertie propertie) {
        this(propertie.getId(), propertie.getQuantityRoom(), propertie.getPrice(),
                propertie.getDescription(), propertie.getType(),propertie.getPropertyType(),
                new DataProprietor(propertie.getProprietor()),new DataAddressDTO(propertie.getAddress()));
    }
}

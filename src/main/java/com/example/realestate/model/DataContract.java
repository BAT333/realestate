package com.example.realestate.model;

import com.example.realestate.domain.Contract;
import com.example.realestate.domain.ContractType;

import java.math.BigDecimal;

public record DataContract(
        Long id,
        BigDecimal price,

        String description,

        ContractType type,

        DataProprietor old,

        DataProprietor modern,

        DataPropertieSearch properties
) {
    public DataContract(Contract contract) {
        this(contract.getId(), contract.getPrice(),contract.getDescription(),contract.getType(),new DataProprietor(contract.getOld()),new DataProprietor(contract.getModern()),new DataPropertieSearch(contract.getProperties()));
    }
}

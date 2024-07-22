package com.example.realestate.model;

import com.example.realestate.domain.Proprietor;

public record DataProprietor(
        Long id,
        String name,
        String email
) {
    public DataProprietor(Proprietor proprietor) {
        this(proprietor.getId(),proprietor.getName(),proprietor.getEmail());
    }
}

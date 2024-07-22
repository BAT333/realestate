package com.example.realestate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DataProprietorUpdateDTO(
        @Pattern(regexp = "[a-zA-z]{2,255}")
        String name,
        @Email
        String email,
        @Past
        LocalDate birth,
        @Valid
        DataUpdateAddressDTO address
) {
}

package com.example.realestate.model;

import com.example.realestate.domain.Address;
import com.example.realestate.domain.Proprietor;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Optional;

public record DataProprietorDTO(
        @NotNull
        @Pattern(regexp ="[a-zA-Z]{3,255}")
        String name,
        @NotNull
        @Email
        String email,
        @Past
        LocalDate birth,
        @CPF
        @NotNull
        String cpf,
        @Valid
        DataAddressDTO address
) {
    public DataProprietorDTO(Proprietor proprietor) {
        this(proprietor.getName(), proprietor.getEmail(),proprietor.getBirth(), proprietor.getCpf(), new DataAddressDTO(proprietor.getAddress()));
    }
}

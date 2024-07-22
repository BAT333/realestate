package com.example.realestate.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DataRentDTO(
        @NotNull
        String description,
        @NotNull
        @FutureOrPresent
        LocalDate start,
        @FutureOrPresent
        LocalDate end
) {
}

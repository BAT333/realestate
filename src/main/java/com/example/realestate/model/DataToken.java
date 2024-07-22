package com.example.realestate.model;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.context.request.ServletRequestAttributes;

public record DataToken(
       String token

) {
}

package com.example.realestate.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "rents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class RentContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descriptions",nullable = false)
    private String description;
    @Column(name = "start",nullable = false)
    private LocalDate start = LocalDate.now();
    @Column(name = "end",nullable = false)
    private LocalDate end;

}

package com.example.realestate.domain;

import com.example.realestate.model.DataContractUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "contract")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "prices",nullable = false)
    private BigDecimal price;
    @Column(name = "descriptions")
    private String description;
    @Column(name = "types",nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractType type;
    @Column(name = "contract_creation",nullable = false)
    private LocalDate date = LocalDate.now();
    @Column(name ="actives",nullable = false)
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name ="former_owner",nullable = false)
    private Proprietor old;
    @ManyToOne
    @JoinColumn(name ="new_owner",nullable = false)
    private Proprietor modern;
    @OneToOne(fetch = FetchType.LAZY)
    private Propertie properties;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rent",unique = true)
    private RentContract rentContract;


    public Contract(Propertie propertie, Proprietor proprietor,ContractType type) {
        this.price = propertie.getPrice();
        this.description = propertie.getDescription();
        this.type = type;
        this.active = true;
        this.old = propertie.getProprietor();
        this.modern = proprietor;
        this.properties = propertie;
        this.date = LocalDate.now();
    }

    public void update(DataContractUpdateDTO dto) {

        if(dto.description() != null){
            this.description = dto.description();
        }
        if(dto.price() != null){
            this.price = dto.price();
        }
        this.date = LocalDate.now();
    }

    public void delete() {
        this.active = false;
    }
}

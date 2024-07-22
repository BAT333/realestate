package com.example.realestate.domain;

import com.example.realestate.model.DataContractUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Table(name = "property_inspection")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PropertyInspection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descriptions")
    private String description;
    @Column(name = "inspection_date",nullable = false)
    private LocalDate date = LocalDate.now();
    @Column(name ="actives",nullable = false)
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name ="surveyor",nullable = false)
    private Proprietor surveyor;
    @ManyToOne
    @JoinColumn(name ="buyer",nullable = false)
    private Proprietor buyer;
    @OneToOne(fetch = FetchType.LAZY)
    private Propertie properties;
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatusInspection status;

    public PropertyInspection(Propertie propertie, Proprietor proprietor,Proprietor surveyor){
        this.active =true;
        this.date = LocalDate.now();
        this.surveyor =surveyor;
        this.buyer = proprietor;
        this.properties = propertie;
        this.status = ContractStatusInspection.UNREALIZED;
    }


    public void delete() {
        this.active = false;
    }

    public void updete(DataContractUpdateDTO dto) {
        if(dto.description()!= null){
            this.description = dto.description();
            this.status =ContractStatusInspection.ACCOMPLISHED;
        }
    }
}

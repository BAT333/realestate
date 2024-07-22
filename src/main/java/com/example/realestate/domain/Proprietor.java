package com.example.realestate.domain;

import com.example.realestate.model.DataProprietorDTO;
import com.example.realestate.model.DataProprietorUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proprietor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Proprietor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "names",nullable = false)
    private String name;
    @Column(name = "emails",nullable = false,unique = true)
    private String email;
    @Column(name = "births",nullable = false)
    private LocalDate birth;
    @Column(name = "cpfs",nullable = false,unique = true)
    private String cpf;
    @Column(name ="actives",nullable = false)
    private Boolean active = true;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",unique = true,nullable = false)
    private Address address;
    @OneToMany(mappedBy = "proprietor",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Propertie> properties;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logins",nullable = false,unique = true)
    private User login;


    public Proprietor(DataProprietorDTO dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.cpf = dto.cpf();
        this.birth = dto.birth();
        this.active = true;
        this.address = new Address(dto.address());
    }

    public void update(DataProprietorUpdateDTO dto) {
        if(dto.name() != null){this.name = dto.name();}
        if(dto.birth() != null){this.birth = dto.birth();}
        if(dto.email() != null){this.email = dto.email();}
        if(dto.address() != null){this.address.update(dto.address());}

    }

    public void delete() {
        this.active = false;
        this.address.delete();
        this.login.delete();
        this.properties.forEach(Propertie::delete);
    }
}

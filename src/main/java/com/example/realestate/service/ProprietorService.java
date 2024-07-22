package com.example.realestate.service;

import com.example.realestate.config.exceptions.ProprietorExceptions;
import com.example.realestate.domain.Proprietor;
import com.example.realestate.domain.User;
import com.example.realestate.domain.UserRole;
import com.example.realestate.model.DataProprietor;
import com.example.realestate.model.DataProprietorDTO;
import com.example.realestate.model.DataProprietorUpdateDTO;
import com.example.realestate.repository.ProprietorRepository;
import com.example.realestate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProprietorService {

    @Autowired
    private ProprietorRepository repository;
    @Autowired
    private UserRepository userRepository;
    public DataProprietor registerProprietor(DataProprietorDTO dto) {
        if(repository.existsByCpf(dto.cpf())||repository.existsByEmail(dto.email())){
            throw new ProprietorExceptions();
        }
        var proprietor = repository.save(new Proprietor(dto));
        //fazer validação, e passar para microserviço
        var user =  userRepository.save(new User(dto.email(),this.encoder(dto.birth()), UserRole.SUB));
        proprietor.setLogin(user);
        return new DataProprietor(proprietor);
    }

    private String encoder(LocalDate birth) {
        return new BCryptPasswordEncoder().encode(birth.toString());
    }

    public Page<DataProprietor> listProprietor(Pageable pageable) {
        return this.repository.findByActiveTrue(pageable).map(DataProprietor::new);
    }

    public DataProprietorDTO updateProprietor(DataProprietorUpdateDTO dto, Long id) {
        if(!repository.existsByIdAndActiveTrue(id)||repository.existsByEmail(dto.email())){
            throw new ProprietorExceptions();
        }
        var proprietor = this.repository.findByIdAndActiveTrue(id);
        if(proprietor.isPresent()){
            proprietor.get().update(dto);
            return new DataProprietorDTO(proprietor.get());
        }
        return null;
    }

    public void deleteProprietor(Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new ProprietorExceptions();
        }
        var proprietor = this.repository.findByIdAndActiveTrue(id);
        proprietor.ifPresent(Proprietor::delete);
    }

    public DataProprietor proprietor(Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new ProprietorExceptions();
        }
        return this.repository.findByIdAndActiveTrue(id).map(DataProprietor::new).orElse(null);
    }
}

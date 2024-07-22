package com.example.realestate.service;

import com.example.realestate.config.exceptions.PropertieExceptions;
import com.example.realestate.domain.Propertie;
import com.example.realestate.domain.UserRole;
import com.example.realestate.model.DataPropertie;
import com.example.realestate.model.DataPropertieDTO;
import com.example.realestate.model.DataUpdatePropertieDTO;
import com.example.realestate.repository.PropertieRepository;
import com.example.realestate.repository.ProprietorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropertieService {
    @Autowired
    private
    PropertieRepository repository;
    @Autowired
    private
    ProprietorRepository proprietorRepository;

    public DataPropertie registerPropertie(DataPropertieDTO dto, Long id) {
        if(!proprietorRepository.existsByIdAndActiveTrue(id)){
            throw new PropertieExceptions();
        }
        var proprietor = this.proprietorRepository.findByIdAndActiveTrue(id);
        proprietor.get().getLogin().setRole(UserRole.USER);
        var propertie = this.repository.save(new Propertie(dto,proprietor.get()));
        return new DataPropertie(propertie);

    }

    public Page<DataPropertie> listPropertie(Pageable pageable, Long id) {
        if(!proprietorRepository.existsByIdAndActiveTrue(id)){
            throw new PropertieExceptions();
        }
        var proprietor = this.proprietorRepository.getReferenceByIdAndActiveTrue(id);

        return this.repository.findByActiveTrueAndProprietor(pageable,proprietor).map(DataPropertie::new);
    }

    public DataPropertie updatePropertie(DataUpdatePropertieDTO dto, Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new PropertieExceptions();
        }
        var propertie= this.repository.findByIdAndActiveTrue(id);
        if(propertie.isPresent()){
            propertie.get().update(dto);
            return new DataPropertie(propertie.get());
        }
        return null;
    }

    public void deletePropertie(Long id) {
        if(!repository.existsByIdAndActiveTrue(id)){
            throw new PropertieExceptions();
        }
        this.repository.findByIdAndActiveTrue(id).ifPresent(Propertie::delete);
    }

    public Page<DataPropertie> listPropertieAll(Pageable pageable) {
        return this.repository.findByActiveTrue(pageable).map(DataPropertie::new);

    }
}

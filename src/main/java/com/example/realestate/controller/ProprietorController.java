package com.example.realestate.controller;

import com.example.realestate.model.DataProprietor;
import com.example.realestate.model.DataProprietorDTO;
import com.example.realestate.model.DataProprietorUpdateDTO;
import com.example.realestate.service.ProprietorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
@RequestMapping("/proprietor")
@SecurityRequirement(name = "bearer-key")
public class ProprietorController {
    @Autowired
    private ProprietorService service;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataProprietor> register(@RequestBody @Valid DataProprietorDTO dto, UriComponentsBuilder builder){
        var proprietor = this.service.registerProprietor(dto);
        var uri = builder.path("/proprietor/{id}").buildAndExpand(proprietor.id()).toUri();
        return ResponseEntity.created(uri).body(proprietor);
    }
    @GetMapping
    public ResponseEntity<Page<DataProprietor>> listAll(@PageableDefault(sort = {"id"}) Pageable pageable){
        return ResponseEntity.ok(this.service.listProprietor(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<DataProprietor> list(@PathVariable("id") Long id){
        return ResponseEntity.ok(this.service.proprietor(id));
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataProprietorDTO> update (@RequestBody @Valid DataProprietorUpdateDTO dto, @PathVariable("id") Long id){
        var proprietor = this.service.updateProprietor(dto,id);
        return ResponseEntity.ok(proprietor);

    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id") Long id){
        this.service.deleteProprietor(id);
        return ResponseEntity.noContent().build();
    }
}

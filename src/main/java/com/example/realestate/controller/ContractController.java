package com.example.realestate.controller;

import com.example.realestate.model.DataContract;
import com.example.realestate.model.DataContractUpdateDTO;
import com.example.realestate.model.DataRentDTO;
import com.example.realestate.service.ContractService;
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
@RequestMapping("contract")
@SecurityRequirement(name = "bearer-key")
public class ContractController {
    @Autowired
    private ContractService service;
    @PostMapping("sale/{buyer}/{purchase}")
    @Transactional
    public ResponseEntity<DataContract> sale(@PathVariable("buyer")Long buyer, @PathVariable("purchase")Long purchase, UriComponentsBuilder builder){
        var register = this.service.registerContractSale(buyer,purchase);
        var uri = builder.path("sele/{id}").buildAndExpand(register.id()).toUri();
        return ResponseEntity.created(uri).body(register);
    }
    @PostMapping("rent/{buyer}/{purchase}")
    @Transactional
    public ResponseEntity<DataContract> rent(@PathVariable("buyer")Long buyer, @PathVariable("purchase")Long purchase, @RequestBody @Valid DataRentDTO dto, UriComponentsBuilder builder){
        var register = this.service.registerContractRent(buyer,purchase,dto);
        var uri = builder.path("rent/{id}").buildAndExpand(register.id()).toUri();
        return ResponseEntity.created(uri).body(register);
    }
    @PostMapping("season/{buyer}/{purchase}")
    @Transactional
    public ResponseEntity<DataContract> season(@PathVariable("buyer")Long buyer,@PathVariable("purchase")Long purchase,@RequestBody @Valid DataRentDTO dto,UriComponentsBuilder builder){
        var register = this.service.registerContractSeason(buyer,purchase,dto);
        var uri = builder.path("season/{id}").buildAndExpand(register.id()).toUri();
        return ResponseEntity.created(uri).body(register);
    }
    @GetMapping
    public ResponseEntity<Page<DataContract>> list(@PageableDefault(sort = {"id"})Pageable pageable){
        return ResponseEntity.ok(this.service.list(pageable));
    }
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<DataContract> update(@PathVariable("id")Long id, DataContractUpdateDTO dto){
        return ResponseEntity.ok(this.service.update(id,dto));
    }
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable("id")Long id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();

    }
    @PutMapping("survey/{id}")
    @Transactional
    public ResponseEntity<String> updateSurvey(@PathVariable("id")Long id, DataContractUpdateDTO dto){
        return ResponseEntity.ok(this.service.updateSurvey(id,dto));
    }
}

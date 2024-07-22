package com.example.realestate.controller;

import com.example.realestate.domain.ContractType;
import com.example.realestate.domain.PropertyType;
import com.example.realestate.model.DataPropertie;
import com.example.realestate.model.DataPropertieSearch;
import com.example.realestate.service.SearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("search")
@SecurityRequirement(name = "bearer-key")
public class SearchController {
    @Autowired
    private SearchService service;

    @GetMapping("{contract}")
    public ResponseEntity<Page<DataPropertieSearch>> search(@PathVariable(value = "contract")ContractType type, @RequestParam(name = "type",required = false) List<PropertyType> propertyType, @RequestParam(name = "price", required = false)String price, @PageableDefault(sort = {"id"}) Pageable pageable){

        return ResponseEntity.ok(service.search(type,propertyType,price,pageable));
    }
}
